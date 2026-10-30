package com.example.sensoresnavdrawer;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

public class CamaraActivity extends AppCompatActivity {

    private PreviewView previewView;
    private ImageCapture imageCapture;

    private final ActivityResultLauncher<String> permisoCamara =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    concedido -> {
                        if (concedido) {
                            iniciarCamara();
                        } else {
                            Toast.makeText(this,
                                    "Permiso de cámara denegado",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        previewView = findViewById(R.id.previewView);

        findViewById(R.id.btnFoto).setOnClickListener(v ->
                tomarFoto());

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            iniciarCamara();
        } else {
            permisoCamara.launch(Manifest.permission.CAMERA);
        }
    }

    private void iniciarCamara() {
        ListenableFuture<ProcessCameraProvider> future =
                ProcessCameraProvider.getInstance(this);

        future.addListener(() -> {
            try {
                ProcessCameraProvider provider = future.get();

                Preview preview = new Preview.Builder().build();

                imageCapture = new ImageCapture.Builder().build();

                preview.setSurfaceProvider(
                        previewView.getSurfaceProvider());

                CameraSelector selector =
                        CameraSelector.DEFAULT_BACK_CAMERA;

                provider.unbindAll();

                provider.bindToLifecycle(
                        this,
                        selector,
                        preview,
                        imageCapture);

            } catch (Exception e) {
                Toast.makeText(this,
                        "Error al iniciar cámara",
                        Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void tomarFoto() {
        if (imageCapture == null) return;

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME,
                "Foto_" + System.currentTimeMillis());
        values.put(MediaStore.MediaColumns.MIME_TYPE,
                "image/jpeg");

        ImageCapture.OutputFileOptions options =
                new ImageCapture.OutputFileOptions.Builder(
                        getContentResolver(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values).build();

        imageCapture.takePicture(
                options,
                ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {

                    @Override
                    public void onImageSaved(
                            ImageCapture.OutputFileResults result) {
                        Toast.makeText(CamaraActivity.this,
                                "Fotografía guardada",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(
                            ImageCaptureException error) {
                        Toast.makeText(CamaraActivity.this,
                                "Error al tomar fotografía",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
