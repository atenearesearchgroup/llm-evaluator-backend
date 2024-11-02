package me.loopbreak.hermesanalyzer.utils;

import me.loopbreak.hermesanalyzer.exceptions.InvalidModelOutputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


class PlantParserTest {

    public static final String EXPECTED_RESPONSE_DEFAULT = """
            \s
             @startuml
             skinparam monochrome true
             \s
             class Course {
                 - name: string
                 - credits: int
                 + Prof_imparting: Professor[]
             }
             \s
             class Professor {
                 - name: string
                 + imparts: Course[]
             }
             \s
             class Student {
                 - name: string
                 + enrollsIn: Course[]
             }
             \s
             class Dormitory {
                 - price: float
                 - students: Student[]
                 + accommodates: Student[]
             }
             \s
             Student --|> Course : imparts, enrollsIn
             Professor --|> Course : imparts
             Course ..--< "aggregate": Student : "requires" 5_ Students
             Dormitory ..--< "accommodates": Student
             Professor -- Student
             Student -- "enrolled in": Course
             @enduml""";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getPlantUML_NoDirectives_Valid() {
        String expectedResponse = EXPECTED_RESPONSE_DEFAULT;
        String content = """
                Here is the PlantUML code for the requested class diagram:
                \s
                ```plantuml
                skinparam monochrome true
                \s
                class Course {
                    - name: string
                    - credits: int
                    + Prof_imparting: Professor[]
                }
                \s
                class Professor {
                    - name: string
                    + imparts: Course[]
                }
                \s
                class Student {
                    - name: string
                    + enrollsIn: Course[]
                }
                \s
                class Dormitory {
                    - price: float
                    - students: Student[]
                    + accommodates: Student[]
                }
                \s
                Student --|> Course : imparts, enrollsIn
                Professor --|> Course : imparts
                Course ..--< "aggregate": Student : "requires" 5_ Students
                Dormitory ..--< "accommodates": Student
                Professor -- Student
                Student -- "enrolled in": Course
                ```
                \s
                This PlantUML code defines the classes `Course`, `Professor`, `Student`, and `Dormitory`. The relationships between these classes, as described in your instructions, are also defined. Note that no operations (methods or functions) have been included as requested.""";

        System.out.println(PlantParser.getPlantUML(content));

        assertThat(PlantParser.getPlantUML(content)).isEqualToNormalizingWhitespace(expectedResponse);
    }

    @Test
    void getPlantUML_NoEndUml_Valid() {
        String expectedResponse = EXPECTED_RESPONSE_DEFAULT;
        String content = """
                Here is the PlantUML code for the requested class diagram:
                \s
                ```plantuml
                @startsketch
                skinparam monochrome true
                \s
                class Course {
                    - name: string
                    - credits: int
                    + Prof_imparting: Professor[]
                }
                \s
                class Professor {
                    - name: string
                    + imparts: Course[]
                }
                \s
                class Student {
                    - name: string
                    + enrollsIn: Course[]
                }
                \s
                class Dormitory {
                    - price: float
                    - students: Student[]
                    + accommodates: Student[]
                }
                \s
                Student --|> Course : imparts, enrollsIn
                Professor --|> Course : imparts
                Course ..--< "aggregate": Student : "requires" 5_ Students
                Dormitory ..--< "accommodates": Student
                Professor -- Student
                Student -- "enrolled in": Course
                ```
                \s
                This PlantUML code defines the classes `Course`, `Professor`, `Student`, and `Dormitory`. The relationships between these classes, as described in your instructions, are also defined. Note that no operations (methods or functions) have been included as requested.""";

        System.out.println(PlantParser.getPlantUML(content));

        assertThat(PlantParser.getPlantUML(content)).isEqualToNormalizingWhitespace(expectedResponse);
    }

    @Test
    void getPlantUML_NoStartUml_Valid() {
        String expectedResponse = EXPECTED_RESPONSE_DEFAULT;
        String content = """
                Here is the PlantUML code for the requested class diagram:
                \s
                ```plantuml
                skinparam monochrome true
                \s
                class Course {
                    - name: string
                    - credits: int
                    + Prof_imparting: Professor[]
                }
                \s
                class Professor {
                    - name: string
                    + imparts: Course[]
                }
                \s
                class Student {
                    - name: string
                    + enrollsIn: Course[]
                }
                \s
                class Dormitory {
                    - price: float
                    - students: Student[]
                    + accommodates: Student[]
                }
                \s
                Student --|> Course : imparts, enrollsIn
                Professor --|> Course : imparts
                Course ..--< "aggregate": Student : "requires" 5_ Students
                Dormitory ..--< "accommodates": Student
                Professor -- Student
                Student -- "enrolled in": Course
                @endsketch
                ```
                \s
                This PlantUML code defines the classes `Course`, `Professor`, `Student`, and `Dormitory`. The relationships between these classes, as described in your instructions, are also defined. Note that no operations (methods or functions) have been included as requested.""";

        System.out.println(PlantParser.getPlantUML(content));

        assertThat(PlantParser.getPlantUML(content)).isEqualToNormalizingWhitespace(expectedResponse);
    }

    @Test
    void getPlantUML_AllDirectives_Valid() {
        String expectedResponse = EXPECTED_RESPONSE_DEFAULT;
        String content = """
                Here is the PlantUML code for the requested class diagram:
                \s
                ```plantuml
                @startsketch
                skinparam monochrome true
                \s
                class Course {
                    - name: string
                    - credits: int
                    + Prof_imparting: Professor[]
                }
                \s
                class Professor {
                    - name: string
                    + imparts: Course[]
                }
                \s
                class Student {
                    - name: string
                    + enrollsIn: Course[]
                }
                \s
                class Dormitory {
                    - price: float
                    - students: Student[]
                    + accommodates: Student[]
                }
                \s
                Student --|> Course : imparts, enrollsIn
                Professor --|> Course : imparts
                Course ..--< "aggregate": Student : "requires" 5_ Students
                Dormitory ..--< "accommodates": Student
                Professor -- Student
                Student -- "enrolled in": Course
                @endsketch
                ```
                \s
                This PlantUML code defines the classes `Course`, `Professor`, `Student`, and `Dormitory`. The relationships between these classes, as described in your instructions, are also defined. Note that no operations (methods or functions) have been included as requested.""";


        System.out.println(PlantParser.getPlantUML(content));

        assertThat(PlantParser.getPlantUML(content)).isEqualToNormalizingWhitespace(expectedResponse);
    }

    @Test
    void getPlantUML_NoCode_Invalid() {
        String content = """
                Here is the PlantUML code for the requested class diagram:
                               \s
                This PlantUML code defines the classes `Course`, `Professor`, `Student`, and `Dormitory`. The relationships between these classes, as described in your instructions, are also defined. Note that no operations (methods or functions) have been included as requested.""";

        assertThatExceptionOfType(InvalidModelOutputException.class).isThrownBy(() -> PlantParser.getPlantUML(content));
    }
}