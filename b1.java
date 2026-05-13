import java.util.ArrayList;
import java.util.Scanner;

interface ICapability {
    void checkPerformance();
}

abstract class Staff {
    protected String id;
    protected String name;
    protected double baseSalary;

    public Staff(String id, String name, double baseSalary) {
        this.id = id;
        this.name = name;
        this.baseSalary = baseSalary;
    }

    public abstract double calculateTotalSalary();

    public void showInfo() {
        System.out.printf("ID: %s | Tên: %-15s | Lương cơ bản: %,.0f | Tổng lương: %,.0f VNĐ\n",
                id, name, baseSalary, calculateTotalSalary());
    }

    public String getId() { return id; }
    public void setName(String name) { this.name = name; }
    public void setBaseSalary(double baseSalary) { this.baseSalary = baseSalary; }
}

class Lecturer extends Staff implements ICapability {
    private int teachingHours;

    public Lecturer(String id, String name, double baseSalary, int teachingHours) {
        super(id, name, baseSalary);
        this.teachingHours = teachingHours;
    }

    @Override
    public double calculateTotalSalary() {
        return baseSalary + (teachingHours * 200000);
    }

    @Override
    public void checkPerformance() {
        System.out.println("Đánh giá Giảng viên: Dựa trên số giờ dạy và phản hồi sinh viên.");
    }

    public void setTeachingHours(int hours) { this.teachingHours = hours; }
}

class AdminStaff extends Staff implements ICapability {
    private double bonus;

    public AdminStaff(String id, String name, double baseSalary, double bonus) {
        super(id, name, baseSalary);
        this.bonus = bonus;
    }

    @Override
    public double calculateTotalSalary() {
        return baseSalary + bonus;
    }

    @Override
    public void checkPerformance() {
        System.out.println("Đánh giá Nhân viên: Dựa trên hiệu quả hoàn thành công việc văn phòng.");
    }

    public void setBonus(double bonus) { this.bonus = bonus; }
}

public class b1 {
    private static ArrayList<Staff> list = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        while (true) {
            System.out.println("\n===== QUẢN LÝ TRUNG TÂM GIÁO DỤC =====");
            System.out.println("1. Thêm mới nhân sự");
            System.out.println("2. Hiển thị danh sách");
            System.out.println("3. Cập nhật thông tin theo ID");
            System.out.println("4. Xóa nhân sự theo ID");
            System.out.println("5. Thoát");
            System.out.print("Mời chọn (1-5): ");

            try {
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1: addStaff(); break;
                    case 2: showList(); break;
                    case 3: updateStaff(); break;
                    case 4: deleteStaff(); break;
                    case 5: System.out.println("Tạm biệt!"); return;
                    default: System.out.println("Lựa chọn không hợp lệ!");
                }
            } catch (Exception e) {
                System.out.println("Lỗi: Vui lòng nhập số nguyên!");
            }
        }
    }

    private static void addStaff() {
        System.out.print("Chọn loại (1-Giảng viên, 2-Hành chính): ");
        int type = Integer.parseInt(sc.nextLine());
        System.out.print("Nhập ID: "); String id = sc.nextLine();
        System.out.print("Nhập Tên: "); String name = sc.nextLine();
        System.out.print("Nhập Lương cơ bản: "); double salary = Double.parseDouble(sc.nextLine());

        if (type == 1) {
            System.out.print("Nhập số giờ dạy: "); int hours = Integer.parseInt(sc.nextLine());
            list.add(new Lecturer(id, name, salary, hours));
        } else {
            System.out.print("Nhập tiền thưởng: "); double bonus = Double.parseDouble(sc.nextLine());
            list.add(new AdminStaff(id, name, salary, bonus));
        }
        System.out.println("Thêm thành công!");
    }

    private static void showList() {
        if (list.isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }
        System.out.println("\n--- DANH SÁCH NHÂN SỰ ---");
        for (Staff s : list) {
            s.showInfo();
            if (s instanceof ICapability) ((ICapability) s).checkPerformance();
        }
    }

    private static void updateStaff() {
        System.out.print("Nhập ID cần sửa: ");
        String id = sc.nextLine();
        for (Staff s : list) {
            if (s.getId().equalsIgnoreCase(id)) {
                System.out.print("Tên mới: "); s.setName(sc.nextLine());
                System.out.print("Lương cơ bản mới: "); s.setBaseSalary(Double.parseDouble(sc.nextLine()));
                if (s instanceof Lecturer) {
                    System.out.print("Giờ dạy mới: "); ((Lecturer) s).setTeachingHours(Integer.parseInt(sc.nextLine()));
                } else if (s instanceof AdminStaff) {
                    System.out.print("Thưởng mới: "); ((AdminStaff) s).setBonus(Double.parseDouble(sc.nextLine()));
                }
                System.out.println("Cập nhật thành công!");
                return;
            }
        }
        System.out.println("Không tìm thấy ID này!");
    }

    private static void deleteStaff() {
        System.out.print("Nhập ID cần xóa: ");
        String id = sc.nextLine();
        boolean removed = list.removeIf(s -> s.getId().equalsIgnoreCase(id));
        if (removed) System.out.println("Đã xóa thành công!");
        else System.out.println("Không tìm thấy ID!");
    }
}