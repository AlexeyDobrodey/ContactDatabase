package com.example.dobrodey.contactdatabase.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dobrodey.contactdatabase.R;
import com.example.dobrodey.contactdatabase.database.Contact;
import com.example.dobrodey.contactdatabase.database.ContactData;
import com.example.dobrodey.contactdatabase.dialogs.DialogInputData;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;



public class ContactFragment extends Fragment {


    public static final String EXTRA_CONTACT = "extra_contact";

    private ListAdapter mAdapterPhones, mAdapterEmails;
    private TextView mFirstLastName;
    private FloatingActionButton mFABAddPhone, mFABAddEmail;
    private FloatingActionMenu mFabMenu;

    private List<ContactData> listPhones;
    private List<ContactData> listEmails;
    private Contact mContact;


    private static final int REQUEST_INPUT_PHONE = 0;
    private static final int REQUEST_INPUT_EMAIL = 1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mContact = (Contact) getArguments().getSerializable(EXTRA_CONTACT);
        }
        listPhones = mContact.getPhones();
        listEmails = mContact.getEmails();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_contact, container, false);

        mFirstLastName = (TextView) view.findViewById(R.id.firstLastName);
        mFirstLastName.setText(mContact.getFirstName() + " " + mContact.getLastName());


        mAdapterPhones = new ListAdapter(listPhones);
        mAdapterEmails = new ListAdapter(listEmails);

        RecyclerView rvListPhones = (RecyclerView) view.findViewById(R.id.list_phones);
        rvListPhones.setLayoutManager(new LinearLayoutManager(getActivity()));

        rvListPhones.setAdapter(mAdapterPhones);
        rvListPhones.setNestedScrollingEnabled(false);

        RecyclerView rvListEmails = (RecyclerView) view.findViewById(R.id.list_emails);
        rvListEmails.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvListEmails.setAdapter(mAdapterEmails);
        rvListEmails.setNestedScrollingEnabled(false);

        mFABAddPhone = (FloatingActionButton) view.findViewById(R.id.fab_add_phone);
        mFABAddPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInputData dialogInputData = DialogInputData.newInstance(R.string.dialog_title_phone,
                        R.string.phone_hint);
                dialogInputData.setTargetFragment(ContactFragment.this, REQUEST_INPUT_PHONE);
                dialogInputData.show(getFragmentManager(), "DIALOG_INPUT_PHONE");
            }
        });

        mFABAddEmail = (FloatingActionButton) view.findViewById(R.id.fab_add_email);
        mFABAddEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInputData dialogInputData = DialogInputData.newInstance(R.string.dialog_title_email,
                        R.string.email_hint);
                dialogInputData.setTargetFragment(ContactFragment.this, REQUEST_INPUT_EMAIL);
                dialogInputData.show(getFragmentManager(), "DIALOG_INPUT_EMAIL");
            }
        });

        mFabMenu = (FloatingActionMenu) view.findViewById(R.id.fab_menu);

        return view;
    }

    private void updateUI() {
        mAdapterEmails.notifyDataSetChanged();
        mAdapterPhones.notifyDataSetChanged();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != Activity.RESULT_OK) {
            mFabMenu.toggle(false);
            return;
        }
        String inputData = data.getExtras().getString(DialogInputData.EXTRA_DATA);

        ContactData contactData = null;
        if(requestCode == REQUEST_INPUT_PHONE) {
            contactData = new ContactData(ContactData.TYPE_PHONE, inputData, mContact);
            listPhones.add(contactData);

        }else if(requestCode == REQUEST_INPUT_EMAIL) {
            contactData = new ContactData(ContactData.TYPE_EMAIL, inputData, mContact);
            listEmails.add(contactData);
        }
        contactData.save();
        updateUI();

        mFabMenu.toggle(false);
    }

    public static ContactFragment newInstance(Contact contact) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_CONTACT, contact);

        ContactFragment infoContactFragment = new ContactFragment();
        infoContactFragment.setArguments(bundle);
        return  infoContactFragment;
    }


    private class ListAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private List<ContactData>listData;
        public ListAdapter(List<ContactData>listData) {
            this.listData = listData;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.row_item, parent, false);


            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.bindData(listData.get(position), listData);
        }

        @Override
        public int getItemCount() {
            return listData.size();
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTxtData;
        private ImageView mBtnDelete;

        private ContactData mContactData;
        private List<ContactData> mList;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTxtData = (TextView) itemView.findViewById(R.id.contentData);
            mBtnDelete = (ImageView) itemView.findViewById(R.id.btnDelete);
            mBtnDelete.setOnClickListener(this);
        }

        public void bindData(ContactData contactData, List<ContactData> list) {
            mTxtData.setText(contactData.getData());
            mContactData = contactData;
            mList = list;
        }

        @Override
        public void onClick(View v) {
            mContactData.delete();
            mList.remove(mContactData);
            updateUI();
        }
    }
}
