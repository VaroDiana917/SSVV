package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.domain.Nota;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.NotaXMLRepository;
import org.example.repository.StudentXMLRepository;
import org.example.repository.TemaXMLRepository;
import org.example.service.Service;
import org.example.validation.NotaValidator;
import org.example.validation.StudentValidator;
import org.example.validation.TemaValidator;
import org.example.validation.Validator;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }


    public void testAddStudentService() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "files\\studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "files\\teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "files\\note.xml");

        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);

        assertEquals(service.saveStudent("100", "new Test", 937), 1);
        assertEquals(service.saveStudent("100", "new Test", 940), 0);

    }
    public void testAddStudentRepo() {
        Validator<Student> studentValidator = new StudentValidator();
        StudentXMLRepository repository = new StudentXMLRepository(studentValidator, "files\\studenti.xml");

        Student student = new Student("100", "test", 937);
        Student studentWrong = new Student("100", "test", 940);
        ;
        assertEquals(repository.save(student),student);
        assertNull(repository.save(studentWrong));

    }
}
