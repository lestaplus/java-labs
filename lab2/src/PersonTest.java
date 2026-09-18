import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public class PersonTest {
    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Person.class)
                .verify();
    }
}
