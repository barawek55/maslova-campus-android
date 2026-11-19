package ru.student.maslova;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class ContactFragment extends Fragment {
    private EditText visitorName;
    private EditText contactAddress;

    public ContactFragment() {
        super(R.layout.fragment_feedback);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        visitorName = view.findViewById(R.id.name_input);
        contactAddress = view.findViewById(R.id.email_input);
        view.findViewById(R.id.send_button).setOnClickListener(button -> submitRequest());
    }

    private void submitRequest() {
        String visitor = visitorName.getText().toString().trim();
        String address = contactAddress.getText().toString().trim();
        if (visitor.isEmpty()) {
            visitorName.setError(getString(R.string.required_field));
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(address).matches()) {
            contactAddress.setError(getString(R.string.invalid_email));
            return;
        }

        String summary = getString(R.string.application_details, visitor, address);
        visitorName.getText().clear();
        contactAddress.getText().clear();
        showConfirmation(summary);
    }

    private void showConfirmation(String summary) {
        AlertDialog confirmation = new AlertDialog.Builder(requireContext())
                .setTitle(R.string.application_title)
                .setMessage(summary)
                .setPositiveButton(R.string.okay, null)
                .create();
        confirmation.show();
    }
}
