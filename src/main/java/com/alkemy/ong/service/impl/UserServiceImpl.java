package com.alkemy.ong.service.impl;

import com.alkemy.ong.auth.utils.JwUtils;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.dto.response.UserResponseDTO;
import com.alkemy.ong.model.Users;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.UserService;
import com.alkemy.ong.service.mapper.UsersMapper;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private JwUtils jwUtils;
    @Autowired
    private MessageSource messageSource;

    public List<UserResponseDTO> getAll() {
        return usersMapper.userEntityListToDTOList(userRepository.findAll());
    }

    @Override
    public Users applyPatchToUser(long id, UserDto patch) {
        Optional<Users> user = userRepository.findById(id);
        if (user.isEmpty()) return null;
        user.ifPresent(userTemp -> {
            userTemp.setFirstName(patch.getFirstName());
            userTemp.setLastName(patch.getLastName());
            userTemp.setPhoto(patch.getPhoto());
            userTemp.setRole(roleRepository.findById(patch.getRoleId()).get());
            userTemp.setEmail(patch.getEmail());
            userTemp.setPassword(patch.getPassword());
        });
        return userRepository.save(user.get());
    }

    @Override
    public UserResponseDTO getUserDataByToken(String token) {
        String email = jwUtils.extractUsername(token.substring(7));
        Users users = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with that email was not found."));
        return usersMapper.userEntityToDTO(users);
    }


    public void deleteUser(Long id) throws Exception {
        Optional<Users> response = userRepository.findById(id);
        if (response.isPresent()) {
            Users users = response.get();
            userRepository.delete(users);
        } else {
            throw new Exception(messageSource.getMessage("error.user.notFound", null, Locale.US));
        }
    }

    @Override
    public UserResponseDTO findById(Long id) {
        Optional<Users> res = userRepository.findById(id);
        if (res.isPresent()) {
            Users user = res.get();
            return usersMapper.userEntityToDTO(user);
        } else {
            throw new EntityNotFoundException(messageSource.getMessage("error.user.notFound", null, Locale.US));
        }
    }

    @Override
    public UserResponseDTO findByEmail(String email) {
        Users user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with that email was not found."));
        return usersMapper.userEntityToDTO(user);
    }

}



