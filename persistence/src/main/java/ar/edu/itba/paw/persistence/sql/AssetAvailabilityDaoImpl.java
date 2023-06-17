package ar.edu.itba.paw.persistence.sql;

//@Repository
/*
public class AssetAvailabilityDaoImpl implements AssetAvailabilityDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;


    @Autowired
    public AssetAvailabilityDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("lendings").usingGeneratedKeyColumns("id");

    }

    @Override
    public LendingImpl borrowAssetInstance(AssetInstanceImpl assetInstance, UserImpl user, LocalDate borrowDate, LocalDate devolutionDate, LendingState lendingState) {
        final Map<String, Object> args = new HashMap<>();
        args.put("assetinstanceid", assetInstance);
        args.put("borrowerId", user);
        args.put("lendDate", java.sql.Date.valueOf(borrowDate));
        args.put("devolutionDate", java.sql.Date.valueOf(devolutionDate));
        args.put("active", lendingState);
        return new LendingImpl(assetInstance, user, borrowDate, devolutionDate, lendingState);
    }

    @Override
    public void changeLendingStatus(LendingImpl lendingId, final LendingState lendingState) {
        */
/*String query = "UPDATE lendings SET active = ? WHERE id = ? ";
        int updatedRows = jdbcTemplate.update(query, lendingState.toString(), lendingId);
        return updatedRows == 1;*//*

    }

}
*/
