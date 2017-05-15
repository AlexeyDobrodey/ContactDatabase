package com.example.dobrodey.contactdatabase.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dobrodey.contactdatabase.R;
import com.example.dobrodey.contactdatabase.database.Contact;
import com.example.dobrodey.contactdatabase.database.Contacts;
import com.example.dobrodey.contactdatabase.dialogs.DialogInputContact;

import java.util.List;


public class ListContactsFragment extends Fragment {

    private String mUserId = null;
    private static final String EXTRA_USER_ID = "userId";

    private static final String DIALOG_INPUT_CONTACT = "InputContact";
    private static final int REQUEST_CONTACT = 0;


    private RecyclerView mRVContacts;
    private ContactsAdapter mAdapter;
    private FloatingActionButton mFAB;
    private TextView mMessageListIsEmpty;

    private Contacts mContacts;
    private List<Contact> mContactList;

    private String mOrder = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mUserId = getArguments().getString(EXTRA_USER_ID);
        }

        mContacts = new Contacts(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_contacts, container, false);

        mAdapter = new ContactsAdapter();
        mRVContacts = (RecyclerView) view.findViewById(R.id.rv_contacts);
        mRVContacts.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRVContacts.setAdapter(mAdapter);
        setSwipeToRecyclerView();
        mMessageListIsEmpty = (TextView)view.findViewById(R.id.messageIsEmpty);

        updateUI();

        mFAB = (FloatingActionButton) view.findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DialogInputContact dialogInputContact = DialogInputContact.newInstance(mUserId);
                dialogInputContact.setTargetFragment(ListContactsFragment.this, REQUEST_CONTACT);
                dialogInputContact.show(fm, DIALOG_INPUT_CONTACT);
            }
        });
        return view;
    }

    private void setSwipeToRecyclerView() {
        ItemTouchHelper.SimpleCallback simpleCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int swipedPosition = viewHolder.getAdapterPosition();
                        Contact contact = mContactList.get(swipedPosition);
                        mContacts.deleteContact(contact);
                        updateUI();
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRVContacts);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == REQUEST_CONTACT) {
            Contact contact = (Contact) data.getSerializableExtra(DialogInputContact.EXTRA_CONTACT);
            mContacts.addContact(contact);
            updateUI();
        }
    }

    private void updateUI() {
        mContactList = mContacts.getContactsSortBy(mUserId, mOrder);
        if(mContactList.isEmpty()) {
            mMessageListIsEmpty.setVisibility(View.VISIBLE);
            mRVContacts.setVisibility(View.GONE);
        }else {
            mMessageListIsEmpty.setVisibility(View.GONE);
            mRVContacts.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void updateOrder(String columnName) {
        mOrder = columnName;
        updateUI();
    }

    public static ListContactsFragment newInstance(String userId) {
        Bundle args = new Bundle();
        args.putString(EXTRA_USER_ID, userId);
        ListContactsFragment listContactsFragment = new ListContactsFragment();
        listContactsFragment.setArguments(args);
        return listContactsFragment;
    }


    protected class ContactsAdapter extends RecyclerView.Adapter<ContactViewHolder> {
        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.contact_row,
                    parent,false);
            return new ContactViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ContactViewHolder holder, int position) {
            Contact contact = mContactList.get(position);
            holder.bindContact(contact);
        }

        @Override
        public int getItemCount() {
            return mContactList.size();
        }
    }

    private class ContactViewHolder extends RecyclerView.ViewHolder{
        private TextView mFirstSecondName, mPhone, mEmail;

        public ContactViewHolder(View itemView) {
            super(itemView);
            mFirstSecondName = (TextView) itemView.findViewById(R.id.firstSecondName);
            mPhone = (TextView) itemView.findViewById(R.id.phone);
            mEmail = (TextView) itemView.findViewById(R.id.email);
        }

        public void bindContact(Contact contact) {
            mFirstSecondName.setText(contact.getFirstName() + " " + contact.getSecondName());
            mPhone.setText(contact.getPhone());
            mEmail.setText(contact.getEmail());
        }
    }
}
