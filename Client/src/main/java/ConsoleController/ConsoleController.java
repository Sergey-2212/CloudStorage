package ConsoleController;

import Handlers.CommandMessage;
import Handlers.FileMessage;
import net.NettyNet;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Scanner;

import static Commands.Command.DELETE_FILE;

public class ConsoleController {

    public static void main(String[] args) {

//        NettyNet net = new NettyNet(msg ->
//        {if(msg instanceof FileMessage) {
//            //TODO
//            }
//
//        if(msg instanceof CommandMessage) {
//           //TODO
//        }
//        });
//        Scanner scanner = new Scanner(System.in);
//        while(scanner.hasNextLine()) {
//            String msg = scanner.nextLine();
       //     net.sendCommand(new CommandMessage(DELETE_FILE, Path.of("1.txt")));
        NettyNet net = new NettyNet(System.out::println);
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            net.sendCommand(new CommandMessage(DELETE_FILE, Path.of("1.txt")));
            System.out.println("Got");
        }
    }
}



