package com.suporte_tecnico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.suporte_tecnico.domain.Tecnico;

//Estendendo JpaRepository pegando a classse Tecnico, e o tipo primitivo do identificador da classe que é um Integer
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
