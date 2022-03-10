## 👀 학습 정리

#### 👉 태블릿 사이즈 추가하기

- res ->
- alt +insert ->
- Android Resource Directory ->
- 기준에 맞는 화면 크기와 orientation 추가하기(공식 문서에 따라 width 600dp와 landscape) ->
- 레이아웃 이름은 반드시 동일하게!

#### 👉 사각형 뷰와 팩토리 구현

##### 사각형 뷰

1. 사각형 뷰 클래스에는 사각형의 속성들을 모두 정의하고 private을 통해 외부에서 접근할 수 없도록 함
2. 해당 속성에 대한 접근은 set 메서드와 get 메서드를 통해서만 가능

##### 사각형 뷰 팩토리

> 팩토리 패턴이란?
>> 클래스의 인스턴스를 대신 만들어주는 팩토리 클래스를 사용하는 패턴

1. 각각의 속성을 정의해주는 메서드를 통해 랜덤값을 넘겨주도록 구현
2. 요구 사항에 따라 view에 대한 참조없이 구현하기 위해 뷰를 생성하는 메서드는 MainActivity로 리팩토링
3. 너비와 높이를 150dp와 120dp로 설정해야 하는데, 코드를 통해서는 px값만 할당할 수 있었음. 그래서 앱을 실행시킬 때, 디바이스의 해상도를 구해 dp를 px로
   변환하는 메서드를 구현하고 활용함

```
private fun dp2px(dp: Float): Float {
        val resources = mainActivity.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
```

#### 👉 버튼 추가하기

1. 미션과 똑같은 버튼을 추가하고 싶었으나 도저히 찾지 못해서 최대한 유사하게 버튼을 구현함



#### 👉 MVP 모델 구현하기
1. View
   1. MainActivity와 CustomView를 View로 구현
2. Presenter
   1. Presenter 클래스를 Presenter로 구현
3. Model
   1. Plane 클래스를 Model로 구현
4. Contract
   1. View와 Presenter를 연결시켜주는 인터페이스. 구글 샘플 코드를 최대한 따라하려고 했음
5. RectangleListener
   1. Model에서 데이터가 변경되면 이를 Presenter에 알려주는 옵저버 인터페이스

#### 👉 옵저버 패턴 구현하기
1. 옵저버 패턴은 이벤트가 발생하는 클래스에서 이벤트가 발생하면 이를 감지할 수 있게 해주는 역할을 한다
2. 여기서는 Model에서 데이터가 변경(이벤트 발생)되면 이 변경을 감지하여 Presenter(이벤트를 수신)에게 전달하여 지정된 메서드를 구현하도록 함
3. 학습하면서 느낀점
   1. 옵저버 패턴에서 이벤트를 발생되는 클래스는 생성자로 옵저버 인터페이스를 지정함
   2. 이벤트를 수신하는 클래스는 타입이 listener가 되야 한다


> 결과
>>![](../../../image/Android/Week09_drawingapp/3.gif)