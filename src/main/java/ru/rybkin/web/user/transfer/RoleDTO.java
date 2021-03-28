package ru.rybkin.web.user.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rybkin.web.user.models.Role;

@Data
@NoArgsConstructor
public class RoleDTO {
    private Long id;
    private String name;

    public Role toRole() {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        return role;
    }

    public static RoleDTO fromRole(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());

        return roleDTO;
    }
}
