import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class MetaBalls extends PApplet {

class Circle
{
    float X;
    float Y;
    float Radius;
    
    float VelX;
    float VelY;
    
    Circle(float X, float Y, float Radius, float VelX, float VelY)
    {
        this.X = X;
        this.Y = Y;
        this.Radius = Radius;
        this.VelX = VelX;
        this.VelY = VelY;
    }
}

boolean Hollow = false;
int GridResolution = 200;
float AALimit = 1.8f;
ArrayList<Circle> Circles = new ArrayList<Circle>();

public void setup()
{
    
    frame.setTitle("MetaBalls");
    noStroke();
    for (int i = 0; i < 18; i++)
    {
        Circles.add(new Circle(random(width), random(height), random(60, 80), random(-1, 1), random(-1, 1)));   
    }
}

public void draw()
{
    background(0);
    
    //Move circles
    for (Circle C : Circles)
    {
        C.X += C.VelX;
        C.Y += C.VelY;
        
        if (C.X < 0 || C.X >= width)
        {
            C.VelX = -C.VelX;
        }
        if (C.Y < 0 || C.Y >= height)
        {
            C.VelY = -C.VelY;
        }
    }
    
    //Draw circles
    float CellWidth = ((float)width/GridResolution);
    float CellHeight = ((float)height/GridResolution);
    for (int x = 0; x < GridResolution; x++)
    {
        for (int y = 0; y < GridResolution; y++)
        {
            float Contrib = 0;
            for (Circle C : Circles)
            {
                Contrib += pow(C.Radius, 2) / (pow(x*CellWidth - C.X, 2) + pow(y*CellHeight - C.Y, 2));
            }
            if (Contrib >= 1 && (!Hollow || Contrib <= AALimit))
            {
                fill(255*map(min(Contrib, AALimit), 1, AALimit, 0, 1), 0, 1);
                rect(ceil(x*CellWidth), ceil(y*CellHeight), ceil(CellWidth), ceil(CellHeight)); 
            }
        }
    }
}

public void keyPressed() 
{
      if (key == 'q')
      {
          AALimit += 0.1f;
      }
      else if (key == 'a')
      {
          if (AALimit > 1)
          {
              AALimit -= 0.1f;   
          }
      }
      if (key == 'w')
      {
          GridResolution++;
      }
      else if (key == 's')
      {
          GridResolution--;   
      }
      if (key == 'e')
      {
          Circles.add(new Circle(random(width), random(height), random(60, 80), random(-1, 1), random(-1, 1)));   
      }
      else if (key == 'd')
      {
          if (Circles.size() > 0)
          {   
              Circles.remove(0);   
          }
      }
      if (key == 'r')
      {
          Hollow = !Hollow;   
      }
}
    public void settings() {  size(800, 800); }
    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[] { "MetaBalls" };
        if (passedArgs != null) {
          PApplet.main(concat(appletArgs, passedArgs));
        } else {
          PApplet.main(appletArgs);
        }
    }
}
