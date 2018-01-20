package hillelee.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Embeddable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements GrantedAuthority{
    private String authority;
}
