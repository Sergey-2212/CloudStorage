package Handlers;

import Commands.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.nio.file.Path;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommandMessage extends AbstractMessage{

    private Command command;
    private Path path;

    private Path newPath;

    public CommandMessage(Command command, Path path) {
        this.command = command;
        this.path = path;
    }

    public CommandMessage(Command command, Path currentPath, Path newPath) {
        this.command = command;
        this.path = currentPath;
        this.newPath = newPath;
    }
}

