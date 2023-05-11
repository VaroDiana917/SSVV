package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.domain.Nota;
import org.example.domain.Pair;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.NotaXMLRepository;
import org.example.repository.StudentXMLRepository;
import org.example.repository.TemaXMLRepository;
import org.example.service.Service;
import org.example.validation.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


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

    public void testAddGoodStudent(){
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "files\\test_files\\test_studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "files\\test_files\\test_teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "files\\test_files\\test_note.xml");

        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);

        fileRepository1.delete("100");
        assertEquals(service.saveStudent("100", "new Test", 937), 0);
    }


    public void testAddStudentService() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "files\\test_files\\test_studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "files\\test_files\\test_teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "files\\test_files\\test_note.xml");

        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);

        assertEquals(service.saveStudent("100", "new Test", 940), 1);
//        fileRepository1.delete("100");

    }
    public void testTC4_GroupNotValid() {
        Validator<Student> studentValidator = new StudentValidator();
        StudentXMLRepository repository = new StudentXMLRepository(studentValidator, "files\\test_files\\test_studenti.xml");

        Student studentWrong1 = new Student("9003", "Beyonce", 939);
        Student studentWrong2 = new Student("9002", "Taylor Swift", 109);

        assertEquals(repository.save(studentWrong1),studentWrong1);
        assertEquals(repository.save(studentWrong2),studentWrong2);
    }

    public void testTC2_IDNotUnique(){
        Validator<Student> studentValidator = new StudentValidator();
        StudentXMLRepository repository = new StudentXMLRepository(studentValidator, "files\\test_files\\test_studenti.xml");

        Student studentDuplicate = new Student("9000", "James Doe", 936);
        repository.save(studentDuplicate);
        assertEquals(repository.save(studentDuplicate),studentDuplicate);
    }

    public void testTC3_NameNotValid(){
        Validator<Student> studentValidator = new StudentValidator();
        StudentXMLRepository repository = new StudentXMLRepository(studentValidator, "files\\test_files\\test_studenti.xml");

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
    public void testAddTemaIncremental() {
        StudentXMLRepository studentXmlRepo= mock(StudentXMLRepository.class);
        TemaValidator temaValidator= new TemaValidator();
        TemaXMLRepository temaXmlRepo= new TemaXMLRepository(temaValidator, "files\\test_files\\test_teme.xml");
        NotaXMLRepository notaXmlRepo= mock(NotaXMLRepository.class);

        Service service = new Service(studentXmlRepo, temaXmlRepo, notaXmlRepo);

        try {
            temaXmlRepo.delete("1");
            assertEquals(service.saveTema("1", "description", 1, 1),0);
            assert (true);
        } catch (ValidationException e) {
            assert (false);
        }
    }

    public void testAddGradeIncremental() {
        StudentXMLRepository studentXmlRepo= mock(StudentXMLRepository.class);
        TemaXMLRepository temaXmlRepo=mock(TemaXMLRepository.class);
        NotaValidator notaValidator = new NotaValidator();
        NotaXMLRepository notaXmlRepo= new NotaXMLRepository(notaValidator, "files\\test_files\\test_note.xml");

        Service service = new Service(studentXmlRepo, temaXmlRepo, notaXmlRepo);

        Student student = new Student("1", "Mihai", 0);
        Tema tema = new Tema("1", "description", 0, 2);

        when(studentXmlRepo.findOne("1")).thenReturn(student);
        when(temaXmlRepo.findOne("1")).thenReturn(tema);

        try {
            notaXmlRepo.delete(new Pair<>("1","1"));
            assertEquals(service.saveNota("1","1", 6.9, 1, "la multi ani" ), 1);
            assert (true);
        } catch (ValidationException e) {
            assert (false);
        }
    }
}
