package ru.rosbank.javaschool.finalprojectback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rosbank.javaschool.finalprojectback.dto.UserProfileResponseDto;
import ru.rosbank.javaschool.finalprojectback.dto.UserResponseDto;
import ru.rosbank.javaschool.finalprojectback.dto.UserSaveRequestDto;
import ru.rosbank.javaschool.finalprojectback.entity.UserEntity;
import ru.rosbank.javaschool.finalprojectback.mapper.UserMapper;
import ru.rosbank.javaschool.finalprojectback.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;
    private final PostService postService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public UserEntity create(String name, String username, String password, String email, String photo, Collection<GrantedAuthority> authorities) {
        return repository.save(
                new UserEntity(0, name, username, encoder.encode(password), email, photo, authorities, true, true, true, true)
        );
    }

    public void removeById(int id) {
        repository.setRemovedById(id);
        postService.removePostsWithRemovedUser(id);
    }

    public UserEntity save(UserSaveRequestDto dto) {
        return repository.save(mapper.dtoToUserEntity(dto, encoder));
    }
}
