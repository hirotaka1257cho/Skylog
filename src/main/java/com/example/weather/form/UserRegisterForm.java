package com.example.weather.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterForm {

    @NotBlank(message = "名前を入力してください")
    private String name;
    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "メールアドレスの形式が不正です")
    private String email;
    @NotBlank(message = "パスワードを入力してください")
    @Size(min = 8, max = 16, message = "パスワードは8文字以上16文字以内で設定してください")
    private String password;
    @NotBlank(message = "確認用パスワードを入力してください")
    private String confirmPassword;

}
