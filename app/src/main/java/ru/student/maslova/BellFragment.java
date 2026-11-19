package ru.student.maslova;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class BellFragment extends Fragment {
    private static final String CHANNEL_ID = "student_updates";
    private final ActivityResultLauncher<String> permissionRequest =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) {
                    postStudentNotice();
                } else {
                    Toast.makeText(requireContext(), R.string.permission_denied, Toast.LENGTH_LONG).show();
                }
            });

    public BellFragment() {
        super(R.layout.fragment_notice);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.notify_button).setOnClickListener(button -> {
            if (Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                permissionRequest.launch(Manifest.permission.POST_NOTIFICATIONS);
            } else {
                postStudentNotice();
            }
        });
    }

    private void postStudentNotice() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    getString(R.string.channel_name), NotificationManager.IMPORTANCE_DEFAULT);
            requireContext().getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
        if (Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.student_details))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.student_details)))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        NotificationManagerCompat.from(requireContext()).notify(1, builder.build());
    }
}
