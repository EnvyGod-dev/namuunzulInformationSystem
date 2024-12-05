import 'package:flutter/material.dart';
import '../models/onboarding_item.dart';
import '../widgets/onboarding_indicator.dart';
import 'auth_screen.dart'; // Import the AuthScreen

class OnboardingScreen extends StatefulWidget {
  const OnboardingScreen({super.key});

  @override
  State<OnboardingScreen> createState() => _OnboardingScreenState();
}

class _OnboardingScreenState extends State<OnboardingScreen> {
  final PageController _pageController = PageController();
  int _currentPage = 0;

  final List<OnboardingItem> _onboardingItems = [
    OnboardingItem(
      imagePath: 'assets/images/screen1.png',
      title: 'Санхүүгийн асуудлыг манай аппаас',
    ),
    OnboardingItem(
      imagePath: 'assets/images/screen2.png',
      title: 'Найдвартай шилжүүлэг',
    ),
    OnboardingItem(
      imagePath: 'assets/images/screen3.png',
      title: 'Таны санхүүгийн баталгаат түлш',
    ),
  ];

  void _onNextPressed() {
    if (_currentPage < _onboardingItems.length - 1) {
      _pageController.nextPage(
        duration: const Duration(milliseconds: 400),
        curve: Curves.easeInOut,
      );
    } else {
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (context) => const AuthScreen()),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          PageView.builder(
            controller: _pageController,
            onPageChanged: (index) {
              setState(() {
                _currentPage = index;
              });
            },
            itemCount: _onboardingItems.length,
            itemBuilder: (context, index) {
              final item = _onboardingItems[index];
              return Column(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  Expanded(
                    flex: 2,
                    child: Center(
                      child: Image.asset(
                        item.imagePath,
                        height: MediaQuery.of(context).size.height * 0.4,
                        fit: BoxFit.contain,
                      ),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 20.0),
                    child: Text(
                      item.title,
                      textAlign: TextAlign.center,
                      style: const TextStyle(
                        fontSize: 20,
                        fontWeight: FontWeight.w500,
                        color: Colors.white,
                      ),
                    ),
                  ),
                  const Spacer(),
                  OnboardingIndicator(
                    currentPage: _currentPage,
                    itemCount: _onboardingItems.length,
                  ),
                  const SizedBox(height: 20),
                  ElevatedButton(
                    onPressed: _onNextPressed,
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.blue,
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(8),
                      ),
                      padding: const EdgeInsets.symmetric(
                        horizontal: 40,
                        vertical: 12,
                      ),
                    ),
                    child: Text(
                      _currentPage == _onboardingItems.length - 1
                          ? 'Finish'
                          : 'Next',
                      style: const TextStyle(fontSize: 16),
                    ),
                  ),
                  const SizedBox(height: 40),
                ],
              );
            },
          ),
        ],
      ),
    );
  }
}
