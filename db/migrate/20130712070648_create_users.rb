class CreateUsers < ActiveRecord::Migration
  def self.up
    create_table :users, :primary_key => 'id' do |t| 
      t.string :firstname
      t.string :lastname
      t.string :username
      t.string :hashed_password
      t.integer:id
      t.date   :dob
      t.integer:phone, :precision => 12
      t.string :email
      t.string :image
      t.string :salt
      t.timestamps
    end
  end

  def self.down
    drop_table :users
  end
end
