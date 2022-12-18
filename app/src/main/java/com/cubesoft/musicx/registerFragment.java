package com.cubesoft.musicx;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.Navigation;
import com.cubesoft.musicx.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class registerFragment extends Fragment {


    EditText ed1,ed2,ed3;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    TextView _toSignIn;




    public registerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        FragmentRegisterBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_register,container,false);
        final View view = binding.getRoot();
        ed1 = view.findViewById(R.id.edt_email);
        ed2 = view.findViewById(R.id.edt_password);
        ed3 = view.findViewById(R.id.edt_repassword);
        _toSignIn = view.findViewById(R.id.txtv_sigin);
        Button register = view.findViewById(R.id.button_register);
        progressDialog = new ProgressDialog(getContext());
        firebaseAuth = FirebaseAuth.getInstance();

        _toSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(view).navigate(R.id.fr);
            }
        });


        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = ed1.getText().toString().trim();
                String password = ed2.getText().toString();
                String repassword = ed3.getText().toString();
                if (password.equals(repassword)) {


                    ed1.setText("");
                    ed2.setText("");
                    ed3.setText("");
                    registerUser(email, password);
                }

                else
                {
                    Toast.makeText(getContext(),"Passwords do not match, Try again.",Toast.LENGTH_SHORT).show();
                }

            }
        });





        return view;
    }


    public void registerUser(String email, String password)
    {
        if(email=="" || password=="")
        {
            Toast.makeText(getContext(),"ENTER A VALID EMAIL/PASSWORD",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Signing you In");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(getContext(),"Successfully registered :)",Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                    Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_infoFragment);
                }

                else if(task.isComplete())
                {
                    Toast.makeText(getContext(),"Email already registered in our records, Try Signing in",Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }

            }
        });





    }

}
