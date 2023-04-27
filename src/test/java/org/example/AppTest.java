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
import org.example.validation.*;


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

        assertEquals(service.saveStudent("100", "new Test", 940), 1);

    }
    public void testTC4_GroupNotValid() {
        Validator<Student> studentValidator = new StudentValidator();
        StudentXMLRepository repository = new StudentXMLRepository(studentValidator, "files\\studenti.xml");

        Student studentWrong1 = new Student("9003", "Beyonce", 939);
        Student studentWrong2 = new Student("9002", "Taylor Swift", 109);

        assertEquals(repository.save(studentWrong1),studentWrong1);
        assertEquals(repository.save(studentWrong2),studentWrong2);
    }

    public void testTC2_IDNotUnique(){
        Validator<Student> studentValidator = new StudentValidator();
        StudentXMLRepository repository = new StudentXMLRepository(studentValidator, "files\\studenti.xml");


        Student studentDuplicate = new Student("9000", "James Doe", 936);
        repository.save(studentDuplicate);
        assertEquals(repository.save(studentDuplicate),studentDuplicate);
    }

    public void testTC3_NameNotValid(){
        Validator<Student> studentValidator = new StudentValidator();
        StudentXMLRepository repository = new StudentXMLRepository(studentValidator, "files\\studenti.xml");

        Student studentWrong = new Student("9001", "", 936);
        assertEquals(repository.save(studentWrong),studentWrong);
    }


    public void testTC1_ValidateTema(){
        Validator<Tema> temaValidator = new TemaValidator();
        Tema tema = new Tema("400","Tema",7,6);
        temaValidator.validate(tema);

    }
    public void testTC2_ValidateTema(){
        Validator<Tema> temaValidator = new TemaValidator();
        Tema tema = new Tema(null,"Tema",7,6);
        try {
            temaValidator.validate(tema);
            fail("Expected ValidationException was not thrown");
        } catch (ValidationException e) {
            assertEquals("ID invalid! \n", e.getMessage());
        }

    }

    public void testTC3_ValidateTema(){
        Validator<Tema> temaValidator = new TemaValidator();
        Tema tema = new Tema("401","",7,6);
        try {
            temaValidator.validate(tema);
            fail("Expected ValidationException was not thrown");
        } catch (ValidationException e) {
            assertEquals("Descriere invalida! \n", e.getMessage());
        }

    }

    public void testTC4_ValidateTema(){
        Validator<Tema> temaValidator = new TemaValidator();
        Tema tema = new Tema("401","Descriere",6,9);
        try {
            temaValidator.validate(tema);
            fail("Expected ValidationException was not thrown");
        } catch (ValidationException e) {
            assertEquals("Deadline invalid! \n", e.getMessage());
        }
    }

    public void testTC5_ValidateTema(){
        Validator<Tema> temaValidator = new TemaValidator();
        Tema tema = new Tema("401","Descriere",7,0);
        try {
            temaValidator.validate(tema);
            fail("Expected ValidationException was not thrown");
        } catch (ValidationException e) {
            assertEquals("Data de primire invalida! \n", e.getMessage());
        }
    }
}
