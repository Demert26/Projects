package com.revature.data;

import com.revature.entity.Pet;

import java.sql.*;
import java.util.List;

// Here, we are going to implement some methods that interact with the database:
public class PetDaoImpl implements PetDao{

    Connection connection;

    // constructor initialize the connection based on our connection factory:
    public PetDaoImpl() {
        connection = ConnectionFactory.getConnection();
    }

    // take in a pet object and return the pet with the updated id
    @Override
    public Pet insert(Pet pet) {
        // As the code is right now, we insert the same pet over and over again
        // String sql = "insert into pet (id, name, species, food) values (default, 'Ashes', 'cat', 'tuna');";

        // This is dangerous, SQLInjection, if we use string concatenation, bad people can sneak in very dangerous commands:
        // very unpredicatable
//        String sql = "insert into pet(id, name, species, food) values (default, " + pet.getName() + "," +  pet.getFood();
//        System.out.println(sql);

        // wherever we want to include a value, we leave it as a question mark for now:
        String sql = "insert into pet (id, name, species, food) values (default, ?, ?, ?);";

        try {
            // here, we pass in a flag that tells the program that we want to return the generated id:
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // taking our statement and setting up the values based on where we put the ?
            preparedStatement.setString(1, pet.getName());
            preparedStatement.setString(2,pet.getSpecies());
            preparedStatement.setString(3, pet.getFood());
            System.out.println(preparedStatement.toString());

            // we've set up the string but we haven't executed the statement
            // we use the executeUpdate method whenever we do DML operations (insert, update, delete):
            // going to return the number of records that were updated (or in this case inserted)
            int count = preparedStatement.executeUpdate();
            // we've successfully inserted a single record
            if(count == 1) {
                System.out.println("Pet added successfully.");
                // Now, we have to get the id that was generated by the database
                // whenever we get a return value from the database, we store it in a result set:
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                // right when we get our result set, it actually points to nothing, so we increment it:
                resultSet.next();
                /**
                 * Generated id might look like this:
                 * -------
                 * |  1  |
                 * -------
                 */
                // because result sets in general can return multiple values, we have to specify which one we want
                // but in this case, there's only one value so we take the first one
                int generatedId = resultSet.getInt(1);
                // set the id to the original object:
                pet.setId(generatedId);
            }
            else {
                System.out.println("Something went wrong with the insert!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong when preparing the statement!");
        }

        return pet;
    }

    @Override
    public Pet getById(int id) {
        return null;
    }

    @Override
    public List<Pet> getAllPets() {
        return null;
    }

    @Override
    public Pet update(Pet pet) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    // CRUD Methods:


}
