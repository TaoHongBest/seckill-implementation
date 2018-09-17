// js code for primary interactive logic
// javascript modularity
// seckill.detail.init(params);
var seckill = {
    // Package the urls of sekill-related ajax
    URL: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (seckillId) {
            return '/seckill/' + seckillId + '/exposer';
        },
        execution: function (seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/execution';

        }
    },

    // Acquire seckill address, control displaying logic, execute seckill
    handleSeckillKill: function (seckillId, node) {
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">Start to seckill!</button>');
        $.post(seckill.URL.exposer(seckillId), {}, function (result) {
            // In recursed method, execute the interaction process
            if (result && result['success']) {
                var exposer = result['data'];
                if (exposer['exposed']) {
                    // Seckill starts
                    // Acquire seckill address
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId, md5);
                    console.log("killUrl" + killUrl);
                    // Bind click event only one time
                    $('#killBtn').one('click', function () {
                        // Execute seckill request
                        // 1: Disable the button
                        $(this).addClass('disabled');
                        // 2: Send seckill request
                        $.post(killUrl, {}, function (result) {
                            if (result && result['success']) {
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                // 3: Display seckill result
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }
                        });
                    });
                    node.show();
                } else {
                    // Seckill hasn't started
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    // Re-start countdown by the given times
                    seckill.countdown(seckillId, now, start, end);
                }


            } else {
                console.log('result:' + result);
            }


        });
    },

    // Validate phone number
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },


    countdown: function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        // Time determination
        if (nowTime > endTime) {
            // seckill ends
            seckillBox.html('Seckill End!');
        } else if (nowTime < startTime) {
            // seckill hasn't started, bind countdown event
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function (event) {
                // Time format
                var format = event.strftime('Seckill Countdown: %D days %H hours %M mins %S secs');
                seckillBox.html(format);
            }).on('finish.countdown', function () {
                seckill.handleSeckillKill(seckillId, seckillBox);
            });
        } else {
            // seckill starts
            seckill.handleSeckillKill(seckillId, seckillBox);
        }
    },

    // Logic of detail page
    detail: {
        // Initialize the detail page
        init: function (params) {
            // Phone number validation and login, time interaction
            // Planning for the interactive processes
            // Search phone number in cookie
            var killPhone = $.cookie('killPhone');

            if (!seckill.validatePhone(killPhone)) {
                // Bind phone number
                // Control output
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    // Display prompt out layer
                    show: true,
                    backdrop: 'static', // Forbid closing the location
                    keyboard: false // Close keyboard event
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    console.log('inputPhone=' + inputPhone); //TODO
                    if (seckill.validatePhone(inputPhone)) {
                        // write phone number to cookie
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                        // Refresh page
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">Invalid Phone Number!</label>').show(300);
                    }
                });
            }
            // Have logged in
            // Countdown interaction
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(seckill.URL.now(), {}, function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];
                    // Time determination, timing interaction
                    seckill.countdown(seckillId, nowTime, startTime, endTime);
                } else {
                    console.log('result:' + result);
                }
            });
        }
    }

};

