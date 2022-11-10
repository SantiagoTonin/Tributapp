
package com.egg.tributapp.RepositorioEmpresasIt;


import com.egg.tributapp.Entidades.EmpresasIt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioEmpresasIt extends JpaRepository<EmpresasIt, String> {

}
