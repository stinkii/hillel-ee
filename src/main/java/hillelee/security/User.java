package hillelee.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "usr")
@NoArgsConstructor
public class User implements UserDetails{

    @Id
    String username;
    String password;
    @ElementCollection(fetch = FetchType.EAGER)
    Set<Authority> authorities = new HashSet<>();

    public User(String username, String password, String authority) {
        this.username = username;
        this.password = password;
        authorities.add(new Authority(authority));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
