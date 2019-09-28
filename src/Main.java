import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;


class Main  {
        static RandomAccessFile file;
        private  static final ReentrantLock thread=new ReentrantLock(true);
    private static int i = 0;
    private static final Scanner scan = new Scanner(System.in);
     static final LinkedHashMap<Integer, Index_Record> index = new LinkedHashMap<>();
   private static final ArrayList<create_account> account= new ArrayList<>();
   private static create_account temp_account= new create_account(0,"x",0);
    public static void main(String[] args) throws IOException {
        int choice;
        boolean Quit = false;
        while (!Quit) {
            System.out.println("1) Open new account");
            System.out.println("2) Deposit");
            System.out.println("3) Withdrawal");
            System.out.println("4) Bank details ");
            System.out.println("5) Account Statement");
            System.out.println("6) Exit");
            System.out.println("Enter your choice:");
            choice = scan.nextInt();
            scan.nextLine();
            switch (choice) {
                case 1:
                    new_account();
                    break;
                case 2:
                    Deposit();
                    break;
                case 3:
                    Withdrawal();
                    break;
                case 4:
                    details();
                    break;
                case 5:
                     Statement();
                     break;
                case 6:
                    write();
                    Quit =true;
                    System.out.println("Thanks you coming");
                    break;
                default:
                     System.out.println("Invalid choice");
                     break;
            }
        }


    }

    private static int check(int no) {
        if (index.containsKey(no))
            return index.get(no).getStarting_point();
        else
            return -1;
    }

    private static void new_account() {
        int a;

       do {
            int x=0;
            Random random = new Random();
            StringBuilder str= new StringBuilder();
              while (x < 4) {
                a = random.nextInt(9);
                str.append(a);
                x++;
            }
            a = Integer.parseInt(str.toString());
            } while (-1 != check(a));

        System.out.println("Enter Account Holder Name");
        String name=scan.nextLine();
        System.out.println("Enter the mobile no.");
        long no=scan.nextLong();
        scan.nextLine();
        account.add(new create_account(a,name,no));
        i++;

    }


    private static void Deposit() throws IOException {
        int flag = 1,x;
        System.out.println(" enter account no:");
        int no = scan.nextInt();
        scan.nextLine();
        if (check(no) != -1) {
            System.out.println("enter the amount to Deposit:");
            int balance = scan.nextInt();
            scan.nextLine();
            temp_account = create_account.get_account(no);
            temp_account.add_money(balance);
                    done(temp_account,no);


        } else {
            for(x=0;x<=i;x++)
            {
                if(account.get(x).getAccount_no()==no)
                {
                    System.out.println("enter the amount to Deposit:");
                    int balance=scan.nextInt();
                    scan.nextLine();
                    account.get(x).add_money(balance);
                  flag=0;
                  break;
                }
            }

            if(flag==1)
                System.out.println(" Invalid Account no");

        }

    }

            private static void Withdrawal() throws IOException {
        int flag=1,x;
        System.out.println("enter account no:");
        int no=scan.nextInt();
        scan.nextLine();
        if(check(no)!=-1)
        {
            System.out.println(" enter the amount to Withdrawal:");
            int balance=scan.nextInt();
            scan.nextLine();
            temp_account=create_account.get_account(no);
            temp_account.withdrawal_money(balance);
             done(temp_account,no);
        }
        else {
            for(x=0;x<=i;x++)
            {
                if(account.get(x).getAccount_no()==no)
                {
                    System.out.println(" enter the amount to withdrawal");
                    int balance=scan.nextInt();
                    scan.nextLine();
                    account.get(x).withdrawal_money(balance);
                    flag=0;
                    break;
                }

            }

            if(flag==1)
                System.out.println(" Invalid Account no");

        }
         }

    private static void details() throws IOException {
        int flag=1,x;
        System.out.println("enter account no:");
        int no=scan.nextInt();
        scan.nextLine();

        if(check(no)!=-1)
        {
          temp_account=create_account.get_account(no);
          System.out.println (temp_account.toString());
        }
        else {
            if (i != 0) {
                for (x = 0; x <= i; x++) {
                    if (account.get(x).getAccount_no() == no) {
                        System.out.println(account.get(x).toString());
                        flag = 0;
                        break;
                    }

                }

                if (flag == 1)
                    System.out.println(" Invalid Account no");

            }
            else
                System.out.println("Invalid Account no");
        }
    }

    private static void Statement() throws IOException {
        int flag=1,x;
        String string;
         String[] state;
        System.out.println("enter account no:");
        int no=scan.nextInt();
        scan.nextLine();
        if(check(no)!=-1)
        {
        temp_account=create_account.get_account(no);
            string= temp_account.getString();

            state=string.split(",");

            for (String s : state)
                System.out.println(s);
        }
        else
            {
            if(i!=0) {
                for (x = 0; x <= i; x++) {
                    if (account.get(x).getAccount_no() == no) {
                        string = account.get(x).getString();
                        state = string.split(",");

                        for (String s : state) {
                            System.out.println(s);
                        }
                        flag = 0;
                        break;
                    }

                }

                if (flag == 1)
                    System.out.println(" Invalid Account no");

            }
            else
                System.out.println(" Invalid Account no");

        }
       }

    private static  void write()
    {
        try ( RandomAccessFile file=new RandomAccessFile("details.dat","rwd"))
            {
            file.writeInt(account.size());
            int  Index_size= account.size()*3*Integer.BYTES;
            int Starting_location=(int)(Index_size+ file.getFilePointer()+Integer.BYTES);
            file.writeInt(Starting_location);
            long Starting_Index=file.getFilePointer();
            int Start_pointer=Starting_location;
            file.seek(Start_pointer);
               int x=0;
               while(x!=i)
               {
                   file.writeInt(account.get(x).getAccount_no());
                   file.writeUTF(account.get(x).getAccount_Holder_Name());
                   file.writeLong(account.get(x).getMobile_no());
                   file.writeInt(account.get(x).getAccount_Balance());
                   file.writeUTF(account.get(x).getString());

                   Index_Record record=new Index_Record(Start_pointer,(int)(file.getFilePointer()-Start_pointer));
                    index.put(account.get(x).getAccount_no(),record);
                    Start_pointer=(int)file.getFilePointer();
                    x++;

               }
               file.seek(Starting_Index);
               for( Integer id: index.keySet())
               {
                   file.writeInt(id);
                   file.writeInt(index.get(id).getStarting_point());
                   file.writeInt(index.get(id).getLength());
               }
            } catch (IOException e) {
            e.printStackTrace();
        }
    }


private static void update(create_account temp, int no) throws IOException {
       thread.lock();
       try {
           System.out.println("Reading or Writing");
           int Start_pointer = index.get(no).getStarting_point();
           file.seek(index.get(no).getStarting_point());
           file.writeInt(temp.getAccount_no());
           file.writeUTF(temp.getAccount_Holder_Name());
           file.writeLong(temp.getMobile_no());
           file.writeInt(temp.getAccount_Balance());
           file.writeUTF(temp.getString());
           file.writeBytes("\n");

           Index_Record record = new Index_Record(Start_pointer, (int) (file.getFilePointer() - Start_pointer));
           index.put(temp.getAccount_no(), record);
       }
       finally {
           thread.unlock();
       }

    }
    private static void done(create_account temp, int no) {
          new Thread((() -> {
            try {
                update(temp, no);
              } catch (IOException e) {
                e.printStackTrace();
              }
                })).start();

         }
}