package njuse.via;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Lf on 2015/7/17.
 */
public class OptionActivity extends Activity {

    int button_show_number=2;
    int button_max_number=4;
    int true_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option);
        switch(button_show_number){
            case 0:findViewById(R.id.option1_layout).setVisibility(View.GONE);
            case 1:findViewById(R.id.option2_layout).setVisibility(View.GONE);
            case 2:findViewById(R.id.option3_layout).setVisibility(View.GONE);
            case 3:findViewById(R.id.option4_layout).setVisibility(View.GONE);
        }
        if(button_show_number==button_max_number){
            findViewById(R.id.btn_option_add).setVisibility(View.GONE);
        }

        ((Button) findViewById(R.id.btn_option1)).setOnClickListener(new OptionButtonListener());
        ((Button) findViewById(R.id.btn_option1)).setOnLongClickListener(new OptionButtonListener());
        ((Button) findViewById(R.id.btn_option2)).setOnClickListener(new OptionButtonListener());
        ((Button) findViewById(R.id.btn_option2)).setOnLongClickListener(new OptionButtonListener());
        ((Button) findViewById(R.id.btn_option3)).setOnClickListener(new OptionButtonListener());
        ((Button) findViewById(R.id.btn_option3)).setOnLongClickListener(new OptionButtonListener());
        ((Button) findViewById(R.id.btn_option4)).setOnClickListener(new OptionButtonListener());
        ((Button) findViewById(R.id.btn_option4)).setOnLongClickListener(new OptionButtonListener());

        true_id=R.id.btn_option1;
        Button but=(Button)findViewById(true_id);
        but.setBackgroundResource(R.drawable.btn_choose_selector);
    }

    /**
     * ������ť�ĵ������
     * @param view
     */
    public void cancelOptionBtnListener(View view) {
        this.finish();
    }

    public void delOptionListener(View view){
        int note=view.getId();
        Button but1=(Button) findViewById(R.id.btn_option1);
        Button but2=(Button) findViewById(R.id.btn_option2);
        Button but3=(Button) findViewById(R.id.btn_option3);
        Button but4=(Button) findViewById(R.id.btn_option4);
        switch(note){
            case R.id.btn_option1_del1:
                but1.setText(but2.getText());
                if(true_id==but2.getId()){
                    but1.setBackgroundResource(R.drawable.btn_choose_selector);
                    but2.setBackgroundResource(R.drawable.btn_selector);
                    true_id=but1.getId();
                }else if(true_id==but1.getId()){
                    but1.setBackgroundResource(R.drawable.btn_selector);
                    true_id=0;
                }
            case R.id.btn_option1_del2:
                but2.setText(but3.getText());
                if(true_id==but3.getId()){
                    but2.setBackgroundResource(R.drawable.btn_choose_selector);
                    but3.setBackgroundResource(R.drawable.btn_selector);
                    true_id=but2.getId();
                }else if(true_id==but2.getId()){
                    but2.setBackgroundResource(R.drawable.btn_selector);
                    true_id=0;
                }
            case R.id.btn_option1_del3:
                but3.setText(but4.getText());
                if(true_id==but4.getId()){
                    but3.setBackgroundResource(R.drawable.btn_choose_selector);
                    but4.setBackgroundResource(R.drawable.btn_selector);
                    true_id=but3.getId();
                }else if(true_id==but3.getId()){
                    but3.setBackgroundResource(R.drawable.btn_selector);
                    true_id=0;
                }
        }
        switch(button_show_number){
            case 1:findViewById(R.id.option1_layout).setVisibility(View.GONE);break;
            case 2:findViewById(R.id.option2_layout).setVisibility(View.GONE);break;
            case 3:findViewById(R.id.option3_layout).setVisibility(View.GONE);break;
            case 4:findViewById(R.id.option4_layout).setVisibility(View.GONE);break;
        }
        if(button_show_number==button_max_number){
            findViewById(R.id.btn_option_add).setVisibility(View.VISIBLE);
        }

        button_show_number--;
    }

    public void addOptionListener(View view){
        button_show_number++;
        switch(button_show_number){
            case 1:
                Button but1=(Button)findViewById(R.id.btn_option1);
                but1.setText(R.string.option1);
                but1.setBackgroundResource(R.drawable.btn_selector);
                findViewById(R.id.option1_layout).setVisibility(View.VISIBLE);
                break;
            case 2:
                Button but2=(Button)findViewById(R.id.btn_option2);
                but2.setText(R.string.option2);
                but2.setBackgroundResource(R.drawable.btn_selector);
                findViewById(R.id.option2_layout).setVisibility(View.VISIBLE);
                break;
            case 3:
                Button but3=(Button)findViewById(R.id.btn_option3);
                but3.setText(R.string.option3);
                but3.setBackgroundResource(R.drawable.btn_selector);
                findViewById(R.id.option3_layout).setVisibility(View.VISIBLE);
                break;
            case 4:
                Button but4=(Button)findViewById(R.id.btn_option4);
                but4.setText(R.string.option4);
                but4.setBackgroundResource(R.drawable.btn_selector);
                findViewById(R.id.option4_layout).setVisibility(View.VISIBLE);
                break;
        }
        if(button_show_number==button_max_number){
            findViewById(R.id.btn_option_add).setVisibility(View.GONE);
        }
    }

    private class OptionButtonListener implements View.OnClickListener, View.OnLongClickListener {

        public void onClick(View view){

        }

        @Override
        public boolean onLongClick(View view) {
            if(true_id!=0){
                Button but1=(Button) findViewById(true_id);but1.setBackgroundResource(R.drawable.btn_selector);
            }
            Button but2=(Button) view;
            but2.setBackgroundResource(R.drawable.btn_choose_selector);
            true_id=view.getId();
            return true;
        }
    }

}
