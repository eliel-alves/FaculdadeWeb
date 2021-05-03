/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.converters.ConverterOrdem;
import br.edu.ifsul.modelo.Curso;
import br.edu.ifsul.modelo.Disciplina;
import java.io.Serializable;
import javax.ejb.Stateful;

/**
 *
 * @author eliel
 */
@Stateful
public class CursoDAO<TIPO> extends DAOGenerico<Curso> implements Serializable{
    
    public CursoDAO(){
        super();
        classePersistente = Curso.class;
        
        // definir as ordens possíveis
        listaOrdem.add(new Ordem("id", "ID", "="));
        listaOrdem.add(new Ordem("nome", "Nome", "like"));
        listaOrdem.add(new Ordem("instituicao.nome", "Instituição", "like"));
        
        // definir a ordem inicial
        ordemAtual = listaOrdem.get(1);
        
        //inicializa o conversor das ordens
        converterOrdem = new ConverterOrdem();
        converterOrdem.setListaOrdem(listaOrdem);
    }
    
    @Override
    public Curso getObjectByID(Object id) throws Exception {
        Curso obj = em.find(Curso.class, id);
        
        // uso para evitar o erro de lazy inicialization exception
        obj.getDisciplinas().size();
        
        obj.getDisciplinas().forEach(dis -> {
            dis.getAlunos().size();
        });
        
        return obj;
    }
    
}
