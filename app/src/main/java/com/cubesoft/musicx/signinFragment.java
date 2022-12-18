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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.cubesoft.musicx.databinding.FragmentSigninBinding;
public class signinFragment extends Fragment
{
    EditText ed1,ed2;
    Button signin;
    FirebaseAuth firebaseAuth;
    TextView tv;
    ProgressDialog progressDialog;




    public signinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        FragmentSigninBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_signin,container,false);
        View view = binding.getRoot();

        ed1 = view.findViewById(R.id.edt_signinEmail);
        ed2 = view.findViewById(R.id.edt_signinPassword);
        signin = view.findViewById(R.id.button_signin);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());
        tv= view.findViewById(R.id.txtv_register);


        signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = ed1.getText().toString().trim();
                String password = ed2.getText().toString();
                signin_user(email,password);

            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_registerFragment);
            }
        });








        return view;
    }

    private void signin_user(String email, String password)
    {
        progressDialog.setMessage("Signing you in...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(getContext(),"Successfully signed in :)",Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                    Navigation.findNavController(getView()).navigate(R.id.infoFragment);
                }

                else if(task.isComplete())
                {
                    Toast.makeText(getContext(),"Unable to Signin, Double check your email/password",Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }

            }
        });
    }

}
