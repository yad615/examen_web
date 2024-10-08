package com.tecsup.eval02.aop;

import com.tecsup.eval02.domain.entities.Auditoria;
import com.tecsup.eval02.domain.entities.Departamento;
import com.tecsup.eval02.domain.entities.Usuario;
import com.tecsup.eval02.domain.persistence.AuditoriaDao;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Calendar;

@Component
@Aspect
public class LogginAspecto {

    private Long tx;

    @Autowired
    private AuditoriaDao auditoriaDao;

    @Around("execution(* com.tecsup.eval02.services.*ServiceImpl.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        Long currTime = System.currentTimeMillis();
        tx = System.currentTimeMillis();
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String metodo = "tx[" + tx + "] - " + joinPoint.getSignature().getName();

        if (joinPoint.getArgs().length > 0) {
            logger.info(metodo + "() INPUT:" + Arrays.toString(joinPoint.getArgs()));
        }

        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            logger.error("Error en " + metodo + ": " + e.getMessage(), e);
            throw e;
        }

        logger.info(metodo + "(): tiempo transcurrido " + (System.currentTimeMillis() - currTime) + " ms.");
        return result;
    }

    @After("execution(* com.tecsup.eval02.controllers.*Controller.guardar*(..)) ||" +
            "execution(* com.tecsup.eval02.controllers.*Controller.editar*(..)) ||" +
            "execution(* com.tecsup.eval02.controllers.*Controller.eliminar*(..))")
    public void auditoria(JoinPoint joinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String metodo = joinPoint.getSignature().getName();
        Integer id = null;
        String tabla = null;

        Object[] parametros = joinPoint.getArgs();

        if (parametros.length > 0) {
            if (parametros[0] instanceof Departamento) {
                Departamento departamento = (Departamento) parametros[0];
                id = departamento.getIdDepartamentos();
                tabla = "departamentos";
            } else if (parametros[0] instanceof Usuario) {
                Usuario usuario = (Usuario) parametros[0];
                id = usuario.getIdUsuarios();
                tabla = "usuarios";
            } else if (parametros[0] instanceof String) {

                id = Integer.valueOf((String) parametros[0]);
                tabla = metodo.toLowerCase().contains("usuario") ? "usuarios" : "departamentos";
            }
        }

        if (id != null && (metodo.startsWith("guardar") || metodo.startsWith("editar") || metodo.startsWith("eliminar"))) {
            String traza = "tx[" + tx + "] - " + metodo;
            logger.info(traza + "(): registrando auditoria...");

            auditoriaDao.save(new Auditoria(tabla, id, Calendar.getInstance().getTime(), "usuario", metodo));
        }
    }
}
