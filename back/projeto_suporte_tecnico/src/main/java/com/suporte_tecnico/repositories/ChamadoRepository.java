package com.suporte_tecnico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.suporte_tecnico.domain.Chamado;

//Estendendo JpaRepository pegando a classse Cliente, e o tipo primitivo do identificador da classe que é um Integer
public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

}
