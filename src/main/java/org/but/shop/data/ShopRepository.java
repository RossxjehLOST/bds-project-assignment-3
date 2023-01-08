package org.but.shop.data;



import org.but.shop.api.*;
import org.but.shop.config.DataSourceConfig;
import org.but.shop.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShopRepository {         //mam tu sql metody, všetky views co robím beru udaje z sql databazy tuna

    public PersonAuthView findPersonByEmail(String email) {
        try (Connection connection = DataSourceConfig.getConnection();              //pripojenie na db a vrati to jeden connection
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT email, password" +
                             " FROM bds.person p" +
                             " WHERE p.email = ?")) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToPersonAuth(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find person by ID with addresses failed.", e);
        }
        return null;
    }

    public PersonDetailView findPersonDetailedView(Long personId) {         // uqery na detail view
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id_person, email, name, surename, phone_number, city, zip_code, street, home_number" +
                             " FROM bds.person p" +
                             " LEFT JOIN bds.address a ON p.id_person = a.id_address" +
                             " WHERE p.id_person = ?")) {
            preparedStatement.setLong(1, personId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToPersonDetailView(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find person by ID with addresses failed.", e);
        }
        return null;
    }

    public void removePerson(Long id){                              /// query na delete person
        String removePersonSQL = "DELETE FROM bds.person WHERE id_person = ?";
        System.out.println(removePersonSQL);
        try
            (Connection connection = DataSourceConfig.getConnection();
            PreparedStatement prpstmt = connection.prepareStatement(removePersonSQL)){

                prpstmt.setLong(1, id);
                prpstmt.executeUpdate();
            }
            catch (SQLException e){
            System.out.println("fail");

            }

    }

    public List<PersonBasicView> getPersonsBasicView() {                            //query na basic view
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id_person, email, name, surename, phone_number" +
                             " FROM bds.person");
             ResultSet resultSet = preparedStatement.executeQuery();) {
            List<PersonBasicView> personBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                personBasicViews.add(mapToPersonBasicView(resultSet));
            }
            return personBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Persons basic view could not be loaded.", e);
        }
    }

    public List<PersonFilterView> getPersonFilterView(String text){         /// query na filter person
        System.out.println(text);
        String filter = '%'+text+'%';
        try (Connection connection = DataSourceConfig.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id_person, name, surename, email" +
                             " FROM bds.person"  +
                             " WHERE lower(name) like lower(?) "
             )

        ) {
            preparedStatement.setString(1,filter);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PersonFilterView> personFilterView = new ArrayList<>();
            while (resultSet.next()) {
                personFilterView.add(mapToPersonFilterView(resultSet));
            }
            return personFilterView;
        }
        catch (SQLException e) {
            throw new DataAccessException("Person filter view could not be loaded.", e);
        }
    }

    public List<InjectionView> getInjectionView(String input) {  //SQL INJECTION
        String query = "SELECT id_person,name,surname, age FROM bds.injection WHERE id_person = '" + input + "'";
        // 1'; DROP TABLE bds.injection --
        // 1' OR 1=1 --
        try (Connection connection = DataSourceConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            List<InjectionView> injectionViews = new ArrayList<>();
            System.out.println(statement);
            while (resultSet.next()) {

                injectionViews.add(mapToInjectionView(resultSet));
            }
            return injectionViews;
        } catch (SQLException e) {
            throw new DataAccessException("Find all users SQL failed.", e);
        }
    }

    public void createPerson(PersonCreateView personCreateView) {           // query na create person
        String insertPersonSQL = "INSERT INTO bds.person (name, surename, email, phone_number) VALUES (?,?,?,?)";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            System.out.println(preparedStatement);
            // set prepared statement variables
            preparedStatement.setString(1, personCreateView.getName());
            preparedStatement.setString(2, personCreateView.getSurname());
            preparedStatement.setString(3, personCreateView.getEmail());
            preparedStatement.setInt(4, personCreateView.getPhoneNumber());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException("Creating person failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating person failed operation on the database failed.");
        }
    }

    public void editPerson(PersonEditView personEditView) {             //query na edit person
        String insertPersonSQL = "UPDATE bds.person SET name =?, surename =?, email =? WHERE id_person =?";

        String checkIfExists = "SELECT email FROM bds.person WHERE id_person =?";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, personEditView.getEmail());
            preparedStatement.setString(2, personEditView.getName());
            preparedStatement.setString(3, personEditView.getSurname());
            preparedStatement.setLong(4, personEditView.getId());

            try {

                connection.setAutoCommit(false);

               try (PreparedStatement ps = connection.prepareStatement(checkIfExists, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setLong(1, personEditView.getId());
                    ps.execute();
                } catch (SQLException e) {
                    throw new DataAccessException("This person for edit do not exists.");
                }

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new DataAccessException("Editing member failed.");
                }

                connection.commit();

            } catch (SQLException e) {

                connection.rollback();
            } finally {

                connection.setAutoCommit(true);

            }
        } catch (SQLException e) {
            throw new DataAccessException("Editing person failed operation on the database failed.");
        }
    }



    ////////////////////////////////// mapping

    private PersonAuthView mapToPersonAuth(ResultSet rs) throws SQLException {
        PersonAuthView person = new PersonAuthView();
        person.setEmail(rs.getString("email"));
        person.setPassword(rs.getString("password"));
        return person;
    }

    private PersonBasicView mapToPersonBasicView(ResultSet rs) throws SQLException {
        PersonBasicView personBasicView = new PersonBasicView();
        personBasicView.setId(rs.getLong("id_person"));
        personBasicView.setEmail(rs.getString("email"));
        personBasicView.setName(rs.getString("name"));
        personBasicView.setSurname(rs.getString("surename"));
        personBasicView.setPhoneNumber(rs.getString("phone_number"));
        return personBasicView;
    }

    private PersonDetailView mapToPersonDetailView(ResultSet rs) throws SQLException {
        PersonDetailView personDetailView = new PersonDetailView();
        personDetailView.setId(rs.getLong("id_person"));
        personDetailView.setEmail(rs.getString("email"));
        personDetailView.setName(rs.getString("name"));
        personDetailView.setSurname(rs.getString("surename"));
        personDetailView.setPhoneNumber(rs.getString("phone_number"));
        personDetailView.setCity(rs.getString("city"));
        personDetailView.setZipCode(rs.getString("zip_code"));
        personDetailView.setStreet(rs.getString("street"));
        personDetailView.setHouseNumber(rs.getString("home_number"));
        return personDetailView;
    }

    private PersonFilterView mapToPersonFilterView(ResultSet rs) throws SQLException{
        PersonFilterView personFilterView = new PersonFilterView();
        personFilterView.setId(rs.getLong("id_person"));
        personFilterView.setName(rs.getString("name"));
        personFilterView.setSurname(rs.getString("surename"));
        personFilterView.setEmail(rs.getString("email"));
        return personFilterView;
    }

    private InjectionView mapToInjectionView(ResultSet rs ) throws  SQLException{
        InjectionView injectionView = new InjectionView();
        injectionView.setId(rs.getLong("id_person"));
        injectionView.setName(rs.getString("name"));
        injectionView.setSurname(rs.getString("surname"));
        injectionView.setAge(rs.getLong("age"));
        return injectionView;
    }


    }
