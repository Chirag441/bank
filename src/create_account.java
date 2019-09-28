import java.io.IOException;
import java.io.RandomAccessFile;
class create_account extends Main {



    static{

        try {
              file=new RandomAccessFile("details.dat","rwd");

              int point=file.readInt();
              long start=file.readInt();
            while(start >file.getFilePointer()) {
                 int location_Id = file.readInt();

                int location_starting_point = file.readInt();

                int location_length = file.readInt();

                Index_Record record = new Index_Record(location_starting_point, location_length);
                index.put(location_Id, record);
            }

          } catch (IOException e) {
            System.out.println("File does not exists");
        }
      }

    private create_account(String account_Holder_Name, long mobile_no, int account_Balance, int account_no, String string) {
        Account_Holder_Name = account_Holder_Name;
        Mobile_no = mobile_no;
        Account_Balance = account_Balance;
        Account_no = account_no;
        this.string.append(string);

    }

    static  create_account get_account(int value) throws IOException {
            System.out.println("pointer");
              file.seek(index.get(value).getStarting_point());
          int  no=file.readInt();
          String name=file.readUTF();
          long phone=file.readLong();
          int balance=file.readInt();
          String statement=file.readUTF();

        return new create_account(name, phone, balance, no, statement);
      }
     String getString() {
        return string.toString();
    }

     String getAccount_Holder_Name() {
        return Account_Holder_Name;
    }

     long getMobile_no() {
        return Mobile_no;
    }

    int getAccount_Balance() {
        return Account_Balance;
    }

    private final String Account_Holder_Name;
    private long Mobile_no;
    private int Account_Balance;
    private final int Account_no;
     private final StringBuilder  string=new StringBuilder();


    int getAccount_no() {
        return Account_no;
    }

    create_account(int account_no, String account_Holder_Name, long mobile_no) {
        this.Account_no = account_no;
        Account_Holder_Name = account_Holder_Name;
        Mobile_no = mobile_no;
        Account_Balance += 500;
        if(account_no!=0) {
            System.out.println("You Account is  Created successfully\n Account no=" + Account_no +
                    "\n Name =" + Account_Holder_Name + "\n Balance=" + Account_Balance);
            string.append("Your account is created with Rs 500");

        }
    }


    void add_money(int amount) {
        this.Account_Balance += amount;
        System.out.println("Deposit Successfully");
        string.append(",").append("Deposit Rs " ).append( amount).append(" now Balance is Rs" ).append(Account_Balance);
    }

    @Override
    public String toString() {
        return "Your Bank Details are" +
                "\n Account_no=" + Account_no +
                "\n Account_Holder_Name=" + Account_Holder_Name +
                "\nAccount_Balance=" + Account_Balance +
                "\nMobile_no=" + Mobile_no;
    }

    void withdrawal_money(int amount) {


        if ((this.Account_Balance -= amount) <= 0)
            System.out.println("\n Please check your account Balance");
        else
            {
            this.Account_Balance -= amount;
                System.out.println("Withdrawal Successfully");
                string.append(",").append("Withdrawal Rs ").append(amount).append(" now Balance is Rs").append(Account_Balance);
            }

    }



}