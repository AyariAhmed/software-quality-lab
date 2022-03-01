package com.dip;

import java.io.IOException;

public class EncodingModuleClient
{
    public static void main(String[] args) throws IOException
    {
        IReader reader = new MyFileReader(
            "DIP/src/com/dip/beforeEncryption.txt");
        IWriter writer = new MyFileWriter(
            "DIP/src/com/dip/afterEncryption.txt");
        EncodingModule encodingModule = new EncodingModule(reader,writer);    
        encodingModule.encode();

        reader = new MyNetworkReader("http", "myfirstappwith.appspot.com", "/index.html");
        writer = new MyDatabaseWriter();
        EncodingModule encodingModule2 = new EncodingModule(reader,writer);
        encodingModule2.encode();
    }
}
