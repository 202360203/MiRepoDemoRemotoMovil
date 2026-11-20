
package com.example.solitario;

import android.app.Activity;
import android.os.Bundle;

import com.example.solitario.ui.SolitaireView;

public class MainActivity extends Activity {

    private SolitaireView solitaireView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        solitaireView = new SolitaireView(this);

        setContentView(solitaireView);
    }
}
