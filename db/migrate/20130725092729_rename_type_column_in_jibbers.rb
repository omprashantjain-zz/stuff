class RenameTypeColumnInJibbers < ActiveRecord::Migration
  def self.up
    rename_column :jibbers, :type, :jtype
  end

  def self.down
    rename_column :jibbers, :jtype, :type
  end
end
