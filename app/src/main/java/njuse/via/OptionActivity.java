package njuse.via;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import njuse.via.po.Option;
import njuse.via.po.OptionItem;

/**
 * Created by Lf on 2015/7/17.
 */
public class OptionActivity extends Activity {

    int button_show_number;
    int button_max_number=4;
    int true_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        Bundle b=(Bundle) intent.getExtras();
        Option op=(Option) b.get("option");
        setContentView(R.layout.activity_select_option);

        init(op);

    }

    public void init(Option op){

        Button but1 = (Button) findViewById(R.id.btn_option1);
        Button but2 = (Button) findViewById(R.id.btn_option2);
        Button but3 = (Button) findViewById(R.id.btn_option3);
        Button but4 = (Button) findViewById(R.id.btn_option4);

        if(op==null){
            button_show_number=2;
            true_id=but1.getId();

            but1.setText(strToBut(but1.getText().toString(),but1));
            but2.setText(strToBut(but2.getText().toString(),but2));
        }else {

            button_show_number = op.getItemLength();

            int note = op.getTrueLocation();
            switch (note) {
                case 1:
                    true_id = but1.getId();
                    break;
                case 2:
                    true_id = but2.getId();
                    break;
                case 3:
                    true_id = but3.getId();
                    break;
                case 4:
                    true_id = but4.getId();
                    break;
                case 0:
                    true_id = 0;
                    break;
            }

            OptionItem item;
            switch(button_show_number){
                case 4:item=op.getOptionItem(3);but4.setText(strToBut(item.getText(), but4));
                case 3:item=op.getOptionItem(2);but3.setText(strToBut(item.getText(),but3));
                case 2:item=op.getOptionItem(1);but2.setText(strToBut(item.getText(),but2));
                case 1:item=op.getOptionItem(0);but1.setText(strToBut(item.getText(),but1));
            }
        }

        if (true_id != 0) {
            Button but = (Button) findViewById(true_id);
            but.setBackgroundResource(R.drawable.btn_choose_selector);
        }

        switch(button_show_number){
            case 0:findViewById(R.id.option1_layout).setVisibility(View.GONE);
            case 1:findViewById(R.id.option2_layout).setVisibility(View.GONE);
            case 2:findViewById(R.id.option3_layout).setVisibility(View.GONE);
            case 3:findViewById(R.id.option4_layout).setVisibility(View.GONE);
        }
        if(button_show_number==button_max_number){
            findViewById(R.id.btn_option_add).setVisibility(View.GONE);
        }

        but1.setOnClickListener(new OptionButtonListener());
        but1.setOnLongClickListener(new OptionButtonListener());
        but2.setOnClickListener(new OptionButtonListener());
        but2.setOnLongClickListener(new OptionButtonListener());
        but3.setOnClickListener(new OptionButtonListener());
        but3.setOnLongClickListener(new OptionButtonListener());
        but4.setOnClickListener(new OptionButtonListener());
        but4.setOnLongClickListener(new OptionButtonListener());
    }

    public void cancelOptionBtnListener(View view) {
        setEditTextGone();
        this.finish();
    }

    @Override
    public void finish(){
        Option op=new Option();
        OptionItem opItem=new OptionItem();
        Button but;
        boolean boo;

        switch(button_show_number){
            case 4:
                but=(Button) findViewById(R.id.btn_option4);
                if(true_id==but.getId()){
                    boo=true;
                }else{
                    boo=false;
                }
                opItem=new OptionItem(butToStr(but),boo);
                op.insert(0,opItem);
            case 3:
                but=(Button) findViewById(R.id.btn_option3);
                if(true_id==but.getId()){
                    boo=true;
                }else{
                    boo=false;
                }
                opItem=new OptionItem(butToStr(but),boo);
                op.insert(0,opItem);
            case 2:
                but=(Button) findViewById(R.id.btn_option2);
                if(true_id==but.getId()){
                    boo=true;
                }else{
                    boo=false;
                }
                opItem=new OptionItem(butToStr(but),boo);
                op.insert(0,opItem);
            case 1:
                but=(Button) findViewById(R.id.btn_option1);
                if(true_id==but.getId()){
                    boo=true;
                }else{
                    boo=false;
                }
                opItem=new OptionItem(butToStr(but),boo);
                op.insert(0,opItem);
                break;
            case 0:
                op=null;
        }

        Intent intent=new Intent();
        Bundle b=new Bundle();
        b.putSerializable("roption",op);
        intent.putExtras(b);
        setResult(98, intent);
        super.finish();
    }

    public String butToStr(Button but){
        return but.getText().subSequence(2, but.getText().length()).toString();
    }

    public String strToBut(String str,Button but){
        if(but.getId()==true_id){
            return getString(R.string.true_option)+str;
        }else{
            return getString(R.string.false_option)+str;
        }
    }

    public void delOptionListener(View view){

        setEditTextGone();

        int note=view.getId();
        Button but1=(Button) findViewById(R.id.btn_option1);
        Button but2=(Button) findViewById(R.id.btn_option2);
        Button but3=(Button) findViewById(R.id.btn_option3);
        Button but4=(Button) findViewById(R.id.btn_option4);

        switch (note){
            case R.id.btn_option1_del1:
                if(true_id==but1.getId()){
                    Toast.makeText(getApplicationContext(), R.string.del_err_inf,
                            Toast.LENGTH_SHORT).show();
                    return ;
                }
                break;
            case R.id.btn_option1_del2:
                if(true_id==but2.getId()){
                    Toast.makeText(getApplicationContext(), R.string.del_err_inf,
                            Toast.LENGTH_SHORT).show();
                    return ;
                }
                break;
            case R.id.btn_option1_del3:
                if(true_id==but3.getId()){
                    Toast.makeText(getApplicationContext(), R.string.del_err_inf,
                            Toast.LENGTH_SHORT).show();
                    return ;
                }
                break;
            case R.id.btn_option1_del4:
                if(true_id==but4.getId()){
                    Toast.makeText(getApplicationContext(), R.string.del_err_inf,
                            Toast.LENGTH_SHORT).show();
                    return ;
                }
                break;
        }

        switch(note){
            case R.id.btn_option1_del1:
                but1.setText(but2.getText());
                if(true_id==but2.getId()){
                    but1.setBackgroundResource(R.drawable.btn_choose_selector);
                    but2.setBackgroundResource(R.drawable.btn_selector);
                    true_id=but1.getId();
                }
            case R.id.btn_option1_del2:
                but2.setText(but3.getText());
                if(true_id==but3.getId()){
                    but2.setBackgroundResource(R.drawable.btn_choose_selector);
                    but3.setBackgroundResource(R.drawable.btn_selector);
                    true_id=but2.getId();
                }
            case R.id.btn_option1_del3:
                but3.setText(but4.getText());
                if(true_id==but4.getId()){
                    but3.setBackgroundResource(R.drawable.btn_choose_selector);
                    but4.setBackgroundResource(R.drawable.btn_selector);
                    true_id=but3.getId();
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

    public void ensureInputListener(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(OptionActivity.this.getCurrentFocus().getWindowToken()
                , InputMethodManager.HIDE_NOT_ALWAYS);
        view.setVisibility(View.GONE);
        switch(view.getId()){
            case R.id.edt_option1_ent1:
                EditText edt1=(EditText) findViewById(R.id.edt_option1);
                edt1.setVisibility(View.GONE);
                Button but1=(Button) findViewById(R.id.btn_option1);
                but1.setText(strToBut(edt1.getText().toString(), but1));
                but1.setVisibility(View.VISIBLE);
                ImageView img1=(ImageView) findViewById(R.id.btn_option1_del1);
                img1.setVisibility(View.VISIBLE);
                break;
            case R.id.edt_option1_ent2:
                EditText edt2=(EditText) findViewById(R.id.edt_option2);
                edt2.setVisibility(View.GONE);
                Button but2=(Button) findViewById(R.id.btn_option2);
                but2.setText(strToBut(edt2.getText().toString(), but2));
                but2.setVisibility(View.VISIBLE);
                ImageView img2=(ImageView) findViewById(R.id.btn_option1_del2);
                img2.setVisibility(View.VISIBLE);
                break;
            case R.id.edt_option1_ent3:
                EditText edt3=(EditText) findViewById(R.id.edt_option3);
                edt3.setVisibility(View.GONE);
                Button but3=(Button) findViewById(R.id.btn_option3);
                but3.setText(strToBut(edt3.getText().toString(), but3));
                but3.setVisibility(View.VISIBLE);
                ImageView img3=(ImageView) findViewById(R.id.btn_option1_del3);
                img3.setVisibility(View.VISIBLE);
                break;
            case R.id.edt_option1_ent4:
                EditText edt4=(EditText) findViewById(R.id.edt_option4);
                edt4.setVisibility(View.GONE);
                Button but4=(Button) findViewById(R.id.btn_option4);
                but4.setText(strToBut(edt4.getText().toString(), but4));
                but4.setVisibility(View.VISIBLE);
                ImageView img4=(ImageView) findViewById(R.id.btn_option1_del4);
                img4.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void addOptionListener(View view){

        setEditTextGone();

        button_show_number++;
        switch(button_show_number){
            case 1:
                Button but1=(Button)findViewById(R.id.btn_option1);
                but1.setText(strToBut(getString(R.string.new_option),but1));
                but1.setBackgroundResource(R.drawable.btn_selector);
                findViewById(R.id.option1_layout).setVisibility(View.VISIBLE);
                break;
            case 2:
                Button but2=(Button)findViewById(R.id.btn_option2);
                but2.setText(strToBut(getString(R.string.new_option), but2));
                but2.setBackgroundResource(R.drawable.btn_selector);
                findViewById(R.id.option2_layout).setVisibility(View.VISIBLE);
                break;
            case 3:
                Button but3=(Button)findViewById(R.id.btn_option3);
                but3.setText(strToBut(getString(R.string.new_option),but3));
                but3.setBackgroundResource(R.drawable.btn_selector);
                findViewById(R.id.option3_layout).setVisibility(View.VISIBLE);
                break;
            case 4:
                Button but4=(Button)findViewById(R.id.btn_option4);
                but4.setText(strToBut(getString(R.string.new_option),but4));
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

            setEditTextGone();

            view.setVisibility(View.GONE);
            Button but=(Button) view;
            // 接受软键盘输入的编辑文本或其它视图
            switch (view.getId()){
                case R.id.btn_option1:
                    EditText edt1=(EditText) findViewById(R.id.edt_option1);
                    edt1.setText(butToStr(but));
                    edt1.setVisibility(View.VISIBLE);
                    ImageView del1=(ImageView) findViewById(R.id.btn_option1_del1);
                    del1.setVisibility(View.GONE);
                    ImageView ent1=(ImageView) findViewById(R.id.edt_option1_ent1);
                    ent1.setVisibility(View.VISIBLE);
                    edt1.requestFocus();
                    edt1.findFocus();
                    edt1.setSelection(edt1.getText().length());
                    break;
                case R.id.btn_option2:
                    EditText edt2=(EditText)findViewById(R.id.edt_option2);
                    edt2.setText(butToStr(but));
                    edt2.setVisibility(View.VISIBLE);
                    ImageView del2=(ImageView) findViewById(R.id.btn_option1_del2);
                    del2.setVisibility(View.GONE);
                    ImageView ent2=(ImageView) findViewById(R.id.edt_option1_ent2);
                    ent2.setVisibility(View.VISIBLE);
                    edt2.requestFocus();
                    edt2.findFocus();
                    edt2.setSelection(edt2.getText().length());
                    break;
                case R.id.btn_option3:
                    EditText edt3=(EditText)findViewById(R.id.edt_option3);
                    edt3.setText(butToStr(but));
                    edt3.setVisibility(View.VISIBLE);
                    ImageView del3=(ImageView) findViewById(R.id.btn_option1_del3);
                    del3.setVisibility(View.GONE);
                    ImageView ent3=(ImageView) findViewById(R.id.edt_option1_ent3);
                    ent3.setVisibility(View.VISIBLE);
                    edt3.requestFocus();
                    edt3.findFocus();
                    edt3.setSelection(edt3.getText().length());
                    break;
                case R.id.btn_option4:
                    EditText edt4=(EditText)findViewById(R.id.edt_option4);
                    edt4.setText(butToStr(but));
                    edt4.setVisibility(View.VISIBLE);
                    ImageView del4=(ImageView) findViewById(R.id.btn_option1_del4);
                    del4.setVisibility(View.GONE);
                    ImageView ent4=(ImageView) findViewById(R.id.edt_option1_ent4);
                    ent4.setVisibility(View.VISIBLE);
                    edt4.requestFocus();
                    edt4.findFocus();
                    edt4.setSelection(edt4.getText().length());
                    break;
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if(true_id!=0){
                Button but1=(Button) findViewById(true_id);but1.setBackgroundResource(R.drawable.btn_selector);
                String str1=butToStr(but1);
                true_id=view.getId();
                but1.setText(strToBut(str1,but1));
            }
            Button but2=(Button) view;
            but2.setBackgroundResource(R.drawable.btn_choose_selector);
            String str2=butToStr(but2);
            true_id=view.getId();
            but2.setText(strToBut(str2,but2));
            return true;
        }
    }

    public void setEditTextGone(){

        if(OptionActivity.this.getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(OptionActivity.this.getCurrentFocus().getWindowToken()
                    , InputMethodManager.HIDE_NOT_ALWAYS);
        }

        EditText edt1=(EditText) findViewById(R.id.edt_option1);
        EditText edt2=(EditText) findViewById(R.id.edt_option2);
        EditText edt3=(EditText) findViewById(R.id.edt_option3);
        EditText edt4=(EditText) findViewById(R.id.edt_option4);

        ImageView ent;
        Button but;
        ImageView del;

        if(edt1.getVisibility()==View.VISIBLE){
            edt1.setVisibility(View.GONE);
            ent=(ImageView) findViewById(R.id.edt_option1_ent1);
            ent.setVisibility(View.GONE);
            but=(Button) findViewById(R.id.btn_option1);
            but.setVisibility(View.VISIBLE);
            but.setText(strToBut(edt1.getText().toString(), but));
            del=(ImageView) findViewById(R.id.btn_option1_del1);
            del.setVisibility(View.VISIBLE);
        }
        if(edt2.getVisibility()==View.VISIBLE){
            edt2.setVisibility(View.GONE);
            ent=(ImageView) findViewById(R.id.edt_option1_ent2);
            ent.setVisibility(View.GONE);
            but=(Button) findViewById(R.id.btn_option2);
            but.setVisibility(View.VISIBLE);
            but.setText(strToBut(edt2.getText().toString(), but));
            del=(ImageView) findViewById(R.id.btn_option1_del2);
            del.setVisibility(View.VISIBLE);
        }
        if(edt3.getVisibility()==View.VISIBLE){
            edt3.setVisibility(View.GONE);
            ent=(ImageView) findViewById(R.id.edt_option1_ent3);
            ent.setVisibility(View.GONE);
            but=(Button) findViewById(R.id.btn_option3);
            but.setVisibility(View.VISIBLE);
            but.setText(strToBut(edt3.getText().toString(), but));
            del=(ImageView) findViewById(R.id.btn_option1_del3);
            del.setVisibility(View.VISIBLE);
        }
        if(edt4.getVisibility()==View.VISIBLE){
            edt4.setVisibility(View.GONE);
            ent=(ImageView) findViewById(R.id.edt_option1_ent4);
            ent.setVisibility(View.GONE);
            but=(Button) findViewById(R.id.btn_option4);
            but.setVisibility(View.VISIBLE);
            but.setText(strToBut(edt4.getText().toString(), but));
            del=(ImageView) findViewById(R.id.btn_option1_del4);
            del.setVisibility(View.VISIBLE);
        }

    }

    private class setOnFocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (v.getId() == R.id.edt_option1 || v.getId() == R.id.edt_option2 || v.getId() == R.id.edt_option3 || v.getId() == R.id.edt_option4) {

                if(hasFocus){
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                }else{
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(OptionActivity.this.getCurrentFocus().getWindowToken()
                            , InputMethodManager.HIDE_NOT_ALWAYS);
                }

            }

        }
    }

}
