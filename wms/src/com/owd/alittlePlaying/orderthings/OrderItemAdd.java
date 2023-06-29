package com.owd.alittlePlaying.orderthings;

import java.util.ArrayList;

public class OrderItemAdd {

    public static void main(String[] args) {
        ArrayList<Integer> orderNumbers = new ArrayList<>();
        orderNumbers.add(24515712);
        orderNumbers.add(24516845);
        orderNumbers.add(24516914);
        orderNumbers.add(24516915);
        orderNumbers.add(24516916);
        orderNumbers.add(24516917);
        orderNumbers.add(24517019);
        orderNumbers.add(24517052);
        orderNumbers.add(24517053);
        orderNumbers.add(24517094);
        orderNumbers.add(24517096);
        orderNumbers.add(24517119);
        orderNumbers.add(24517152);
        orderNumbers.add(24517153);
        orderNumbers.add(24517225);
        orderNumbers.add(24517258);
        orderNumbers.add(24517479);
        orderNumbers.add(24517516);
        orderNumbers.add(24518277);
        orderNumbers.add(24518638);
        orderNumbers.add(24519122);
        orderNumbers.add(24519354);
        orderNumbers.add(24519900);
        orderNumbers.add(24519948);
        orderNumbers.add(24520678);
        orderNumbers.add(24520720);
        orderNumbers.add(24520762);
        orderNumbers.add(24520763);
        orderNumbers.add(24520794);

        for(Integer id: orderNumbers){
            try {
//                OrderStatus.addLineItemToExistingOrder(id, 667, "ABW", "Beach Wheel Kit", 1, 199.9f, 0.0f);
            }catch(Exception ex){
                System.out.println("order id " + id + " threw an exception");
                ex.printStackTrace();
            }
        }
    }
}
