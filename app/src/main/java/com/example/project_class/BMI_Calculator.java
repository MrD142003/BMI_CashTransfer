package com.example.project_class;

public class BMI_Calculator {
    private int weight;
    private int height;
    public BMI_Calculator(int weight, int height) {
        this.weight = weight;
        this.height = height;
    }
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double calc(){
        return weight/Math.pow((height*1.0/100),2);
    }

    public String BMIresult(){
        String result = "";
        double r = calc();
        if(r < 18.5){
            result = "Cân nặng thấp gầy";
        }
        else if(r >= 18.5 && r <= 24.9){
            result = "Bình thường";
        }
        else if(r > 24.9 && r <25.5){
            result = "Thừa cân";
        }
        else if(r >= 25.5 && r<= 29.9){
            result = "Tiền béo phì";
        }
        else if(r >=30 && r<= 34.9){
            result ="Béo phì cấp độ I";
        }
        else if(r >= 35 && r <= 39.9){
            result = "Béo phì cấp độ II";
        }
        else if(r >= 40){
            result = "Béo phì cấp độ III";
        }
        return result;
    }
}
