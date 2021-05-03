/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.converters.ConverterOrdem;
import br.edu.ifsul.modelo.Disciplina;
import br.edu.ifsul.modelo.Usuario;
import java.io.Serializable;
import javax.ejb.Stateful;
import javax.persistence.Query;

/**
 *
 * @author eliel
 */
@Stateful
public class DisciplinaDAO<TIPO> extends DAOGenerico<Disciplina> implements Serializable {

    public DisciplinaDAO() {
        super();
        classePersistente = Disciplina.class;

        // definir as ordens possíveis
        listaOrdem.add(new Ordem("id", "ID", "="));
        listaOrdem.add(new Ordem("nome", "Nome", "like"));
        listaOrdem.add(new Ordem("curso.nome", "Curso", "like"));

        // definir a ordem inicial
        ordemAtual = listaOrdem.get(1);

        //inicializa o conversor das ordens
        converterOrdem = new ConverterOrdem();
        converterOrdem.setListaOrdem(listaOrdem);
    }

    @Override
    public Disciplina getObjectByID(Object id) throws Exception {
        Disciplina obj = em.find(Disciplina.class, id);
        
        // uso para evitar o erro de lazy inicialization exception
        obj.getAlunos().size();
        return obj;
    }
    
}
