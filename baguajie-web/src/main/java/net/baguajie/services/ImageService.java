package net.baguajie.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public interface ImageService {

	String put (File image) throws FileNotFoundException;
	
	void get(String resId, OutputStream os) throws IOException;
	
}
