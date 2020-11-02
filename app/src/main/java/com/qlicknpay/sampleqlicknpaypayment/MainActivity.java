package com.qlicknpay.sampleqlicknpaypayment;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity{

    private Spinner spinner, spinner_PM;

    int bank_name;
    int selected_PM = 0;
    String[] list_bank = {};

    EditText AmountET, DescriptionET, InvoiceET;



    //ARRAY STRING FOR PAYMENT METHOD NAME
    static final String[] payment_method_name = {"B2C", "B2B"};

    //LIST OF BANK CODE FOR B2C
    static final String[] list_bank_code_b2c = {"ABB0233", "ABMB0212", "AMBB0209", "BIMB0340", "BKRM0602", "BMMB0341", "BSN0601",
            "BCBB0235", "CIT0219", "HLB0224", "HSBC0223", "KFH0346", "MB2U0227", "MBB0228", "OCBC0229", "PBB0233", "RHB0218", "SCB0216",
    "UOB0226"};

    //LIST OF BANK NAME OF B2C
    static final String[] list_bank_name_b2c = {"Affin Bank", "Alliance Bank (Personal)", "AmBank", "Bank Islam", "Bank Rakyat", "Bank Muamalat", "BSN",
            "CIMB Clicks", "Citibank", "Hong Leong Bank", "HSBC Bank", "KFH", "Maybank2U", "Maybank2E", "OCBC Bank", "Public Bank", "RHB Bank",
            "Standard Chartered", "UOB Bank"};

    //LIST OF BANK CODE OF B2B
    static final String[] list_bank_code_b2b = {"ABB0232", "ABMB0213", "AMBB0208", "BIMB0340", "BMMB0341", "BCBB0235", "CIT0218",
            "DBB0199", "HLB0224", "HSBC0223", "BKRM0602", "KFH0346", "MBB0228", "OCBC0229", "PBB0233", "PBB0234", "RHB0218", "SCB0215",
            "UOB0227", "UOB0228"};

    //LIST OF BANK NAME OF B2B
    static final String[] list_bank_name_b2b = {"Affin Bank", "Alliance Bank (Business)", "AmBank", "Bank Islam", "Bank Muamalat", "CIMB Bank",
            "Citibank Corporate Banking","Deutsche Bank", "Hong Leong Bank", "HSBC Bank", "i-bizRAKYAT", "KFH", "Maybank2E", "OCBC Bank", "Public Bank",
            "PB Enterprise", "RHB Bank", "Standard Chartered", "UOB Bank", "UOB Regional"};


    //ENTER YOUR MERCHANT ID
    int merchant_id = 10000;

    //ENTER YOU API KEY
    String api = "YOURAPIKEY";

    //ENDPOINT URL (PRODUCTION)
    String endpoint_url = "https://www.qlicknpay.com/merchant/api/v1/direct/receiver";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // PAYMENT METHOD SELECTION (FOR TESTING PURPOSE)
        spinner_PM = (Spinner)findViewById(R.id.paymentmethod);
        ArrayAdapter<String>adapter_PM = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,payment_method_name);

        adapter_PM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_PM.setAdapter(adapter_PM);

        spinner_PM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                    change_bank_list(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        // PAYMENT METHOD SELECTION HERE

        //RETRIEVED INPUT FROM THE USER
        AmountET = (EditText)findViewById(R.id.amount);
        DescriptionET = (EditText)findViewById(R.id.description);
        InvoiceET = (EditText)findViewById(R.id.invoice);
        //RETRIEVED INPUT FROM THE USER END HERE
    }

    //CHANGE LIST OF BANK DISPLAYED DEPENDING ON SELECTED PAYMENT METHOD
    public void change_bank_list(int position)
    {
        selected_PM = position;

        if(position == 0)
        {   // IF PAYMENT SELECTED IS B2C, DISPLAY B2C BANK LIST
            list_bank = list_bank_name_b2c;
        }
        else if(position == 1)
        {   // IF PAYMENT SELECTED IS B2B,  DISPLAY B2B BANK LIST
            list_bank = list_bank_name_b2b;
        }

        spinner = (Spinner)findViewById(R.id.bankcode);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,list_bank);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                // GET THE BANK ID SELECT BY USER
                bank_name = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
    //CHANGE LIST OF BANK DISPLAYED DEPENDING ON SELECTED PAYMENT METHOD END HERE

    // WHEN USER CLICK ON PAY BUTTON
    public void OnPay(View view)
    {
        String bank_code = "", payment_method = "";

        //RETRIEVED AMOUNT, PAYMENT DESCRIPTION, AND INVOICE NUMBER FROM USER
        String amount =  AmountET.getText().toString();
        String payment_desc = DescriptionET.getText().toString();
        String invoice = InvoiceET.getText().toString();

        //CHECK SELECTED PAYMENT METHOD
        if(selected_PM == 0)
        {   payment_method = "b2c";
            bank_code = list_bank_code_b2c[bank_name];
        }
        else if(selected_PM == 1)
        {   payment_method = "b2b";
            bank_code = list_bank_code_b2b[bank_name];
        }

        // VALIDATION HASHING FOR PAYMENT VERIFICATION (MANDATORY)
        String hash = md5(api +"|"+ merchant_id +"|"+ invoice +"|"+ amount +"|"+ payment_desc +"|"+ bank_code +"|"+ payment_method);

        // SET ALL MANDATORY DATA INTO ONE STRING URL
        String url = endpoint_url+"?merchant_id="+merchant_id+"&invoice="+invoice+"&amount="+amount+"&payment_desc="+payment_desc+
                "&bank_code="+bank_code+"&payment_method="+payment_method+"&hash="+hash;

        // PROCEED TO NEW ACTIVITY
        Intent myIntent = new Intent(this,Payment.class);

        // SENDING DATA TO THE NEXT ACTIVITY
        Bundle b = new Bundle();
        b.putString("url", url);
        myIntent.putExtras(b);
        finish();
        overridePendingTransition(0, 0);
        this.startActivity(myIntent);
        overridePendingTransition(0, 0);
    }

    // FUNCTION ON HOW TO HASHING THE DATA
    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
