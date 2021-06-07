package fr.formation.afpa.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import fr.formation.afpa.dao.IRoleDao;
import fr.formation.afpa.dao.IUserDao;
import fr.formation.afpa.domain.AppRole;
import fr.formation.afpa.domain.UserProfile;
@Service
public class UserRoleService implements UserDetailsService,IRoleService {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IRoleDao iroleDao;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserProfile appUser = this.userDao.findByLogin(userName);
        if (appUser == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }
        System.out.println("Found User: " + appUser);
        List<String> roleNames = new ArrayList<>();
        // [ROLE_USER, ROLE_ADMIN,..]
        Set<AppRole> roles = appUser.getRoles();
        for (AppRole appRole : roles) {
            roleNames.add(appRole.getRoleName());
        }
        System.out.println("=================roleNames========================" + roleNames);
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }
        UserDetails userDetails = (UserDetails) new User(appUser.getLogin(), //
                appUser.getPassword(), grantList);
        return userDetails;
    }
	@Override
	public Set<AppRole> findByRoleId(Long id) {
		return iroleDao.findByRoleId(id);
	}
   
}