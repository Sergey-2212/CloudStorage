package Handlers;

import Commands.Command;

import java.nio.file.Path;

public class CommandMessage extends AbstractMessage {
        private Command command;
        private Path path;

        private Path newPath;

        public CommandMessage(Command command, Path path) {
                this.command = command;
                this.path = path;
        }

        public CommandMessage(Command command, Path path, Path newPath) {
                this.command = command;
                this.path = path;
                this.newPath = newPath;
        }
}
