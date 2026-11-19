package ru.student.maslova;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class QuestionFragment extends Fragment {
    public QuestionFragment() {
        super(R.layout.fragment_quiz);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.answer_button).setOnClickListener(button -> {
            String[] choices = {"EditText", "BottomNavigationView", "Toast"};
            new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.choose_answer)
                    .setItems(choices, (dialog, selectedIndex) -> {
                        int message = selectedIndex == 1 ? R.string.success : R.string.failure;
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
        });
    }
}
