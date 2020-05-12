package com.example.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;


    public TarefaDAO(Context context) {

        dbHelper db = new dbHelper( context );

        escreve = db.getWritableDatabase(); //permite salvar no banco
        le = db.getReadableDatabase(); //permite ler do banco

    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome",tarefa.getNomeTarefa());

        try {

            escreve.insert(dbHelper.TABELA_TAREFAS,null, cv);
            Log.i("info DB","Tarefa salva com sucesso!  " );
        }catch ( Exception e ){

            Log.i("info DB","Erro ao salvar tarefa  " + e.getMessage() );

            return false;

        }


        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome",tarefa.getNomeTarefa());


        try {

            String[] args = {tarefa.getId().toString()};

            escreve.update(dbHelper.TABELA_TAREFAS, cv,"id=?",args);
            Log.i("info DB","Tarefa atualizada com sucesso!  " );
        }catch ( Exception e ){

            Log.i("info DB","Erro ao atualizar tarefa  " + e.getMessage() );

            return false;

        }



        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try {
            String[] args = {tarefa.getId().toString()};
            escreve.delete(dbHelper.TABELA_TAREFAS,"id=?", args);
            Log.i("info DB","Tarefa removida com sucesso!  " );
        }catch ( Exception e ){

            Log.i("info DB","Erro ao remover tarefa  " + e.getMessage() );

            return false;

        }


        return true;
    }

    @Override
    public List<Tarefa> listar() {


        List<Tarefa> tarefas = new ArrayList<>();

        String  sql = "SELECT * FROM " + dbHelper.TABELA_TAREFAS + " ;";

        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext()){


            Tarefa tarefa = new Tarefa();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString(c.getColumnIndex("nome"));


            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);

            tarefas.add(tarefa);

        }


        return tarefas;
    }
}
