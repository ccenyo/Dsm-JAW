package exeptions;

public class DsmListFolderException extends DsmLoginException{
    public DsmListFolderException(String errorMessage) {
        super(errorMessage);
    }
}