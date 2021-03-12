/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Especialidade;
import java.io.Serializable;
import javax.ejb.Stateful;

/**
 * 
 * 
 *
 * @author eliel
 */
@Stateful
public class EspecialidadeDAO<TIPO> extends DAOGenerico<Especialidade> implements Serializable{
    
    public EspecialidadeDAO(){
        super();
        classePersistente = Especialidade.class;
    }
    
}
