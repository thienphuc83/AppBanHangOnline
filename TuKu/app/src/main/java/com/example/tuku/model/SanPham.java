package com.example.tuku.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    public int Id;
    public String Tensanpham;
    public String Hinhsanpham;
    public int Giasanpham;
    public String Motasanpham;
    public int Idsanpham;

    public SanPham(int id, String tensanpham, String hinhsanpham, int giasanpham, String motasanpham, int idsanpham) {
        Id = id;
        Tensanpham = tensanpham;
        Hinhsanpham = hinhsanpham;
        Giasanpham = giasanpham;
        Motasanpham = motasanpham;
        Idsanpham = idsanpham;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTensanpham() {
        return Tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        Tensanpham = tensanpham;
    }

    public String getHinhsanpham() {
        return Hinhsanpham;
    }

    public void setHinhsanpham(String hinhsanpham) {
        Hinhsanpham = hinhsanpham;
    }

    public int getGiasanpham() {
        return Giasanpham;
    }

    public void setGiasanpham(int giasanpham) {
        Giasanpham = giasanpham;
    }

    public String getMotasanpham() {
        return Motasanpham;
    }

    public void setMotasanpham(String motasanpham) {
        Motasanpham = motasanpham;
    }

    public int getIdsanpham() {
        return Idsanpham;
    }

    public void setIdsanpham(int idsanpham) {
        Idsanpham = idsanpham;
    }
}
