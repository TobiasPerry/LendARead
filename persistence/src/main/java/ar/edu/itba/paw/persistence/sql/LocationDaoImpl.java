package ar.edu.itba.paw.persistence.sql;

import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.itba.edu.paw.persistenceinterfaces.LocationDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Repository
public class LocationDaoImpl implements LocationDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    //@Autowired
    public LocationDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("location").usingGeneratedKeyColumns("id");

    }


    @Override
    public LocationImpl addLocation(LocationImpl lc) {
        final Map<String, Object> args = new HashMap<>();

        args.put("zipcode", lc.getZipcode());
        args.put("locality", lc.getLocality());
        args.put("province", lc.getProvince());
        args.put("country", lc.getCountry());
        args.put("address", lc.getAddress());
       return null;
    }

    @Override
    public List<LocationImpl> getLocations(UserImpl user) {
        return null;
    }

    @Override
    public LocationImpl getLocation(int location) {
        return null;
    }

    @Override
    public LocationImpl editLocation(LocationImpl lc) {
        return null;
    }


}
