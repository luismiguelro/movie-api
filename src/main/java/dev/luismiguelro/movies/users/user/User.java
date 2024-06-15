package dev.luismiguelro.movies.users.user;

import dev.luismiguelro.movies.reviews.Review;
import dev.luismiguelro.movies.users.user.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.EnumType; // !posible error
import jakarta.persistence.Enumerated;

import java.util.Collection;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User implements UserDetails {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    @Indexed(unique = true) // Ensure email is unique
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @DocumentReference
    private List<Review> reviews;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name())) ;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
