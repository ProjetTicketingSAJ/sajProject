package fr.formation.afpa.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import fr.formation.afpa.domain.UserProfile;

@Aspect
@Component
public class LogginAspect {

	// la premiere * c'est le type de méthode (void,integer,etc) la l'étoile c'est
	// pour toutes les méthodes
	// Apres on met le package ciblé apres le *Service c'est pour toutes les classes
	// ...Service dans ton package
	@Pointcut("execution(* fr.formation.afpa.service.*Service*.*(..))")
	public void pointcut1() {

	}

	@Pointcut("within(fr.formation.afpa.service..*)")
	public void pointcut2() {

	}

	@Before("pointcut1()")
	public void adviceBefore() {
		System.out.println("===================================== BEFORE ===========================================");

	}

	@After("pointcut1()")
	public void adviceAfter(JoinPoint jeanpaul) {
		System.out.println("===================================== AFTER ===========================================");
		System.out.println("===============" + jeanpaul);
		for (Object arg : jeanpaul.getArgs()) {
			System.out.println("//////////////ARG = ///////////////" + arg);
		}

	}

	@AfterReturning(value = "pointcut1() && pointcut2()", returning = "result")
	public void adviceAfterReturning(JoinPoint jeanpaul, Object result) {
		System.out.println(
				"===================================== AFTERRETURNING ===========================================");
		if (result instanceof UserProfile) {
			System.out.println("==================== return = " + ((UserProfile) result).getPrenom());
		}
		System.out.println("result = " + result);
	}

	@Around(value = "pointcut1() && pointcut2()")
	public Object adviceAround(ProceedingJoinPoint jeanpaul) throws Throwable {
		System.out.println("===================================== AROUND ===========================================");
		System.out.println("============================================START===================================="
				+ jeanpaul.getSignature().getName());
		Object result = jeanpaul.proceed();
		System.out.println("============================================EXIT===================================="
				+ jeanpaul.getSignature().getName() + result);
		return result;
	}

	@Around(value = "pointcut1() && pointcut2()")
	public Object adviceTimeRespons(ProceedingJoinPoint jeanpaul) throws Throwable {
		long startTime = System.currentTimeMillis();

		Object result = jeanpaul.proceed();

		long timeTaken = System.currentTimeMillis() - startTime;
		System.out.println("Time Taken by : " + timeTaken);
		return result;
	}
}
